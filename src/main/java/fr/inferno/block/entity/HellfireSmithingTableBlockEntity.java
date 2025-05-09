package fr.inferno.block.entity;

import fr.inferno.mod.InfernoMod;
import fr.inferno.packet.ModMessages;
import fr.inferno.packet.handlers.HellforgeUpdatePacket;
import fr.inferno.particles.ParticlesHelper;
import fr.inferno.recipe.HellforgingRecipe;
import fr.inferno.screen.HellfireSmithingTableMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

//import team.lodestar.lodestone.registry.common.particle.*;
//import team.lodestar.lodestone.systems.easing.*;
//import team.lodestar.lodestone.systems.particle.builder.*;
//import team.lodestar.lodestone.systems.particle.data.*;
//import team.lodestar.lodestone.systems.particle.data.color.*;
//import team.lodestar.lodestone.systems.particle.data.spin.*;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;

import java.awt.*;
import java.util.Optional;

public class HellfireSmithingTableBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler inventory = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
         }
    };

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 50;

    public HellfireSmithingTableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.HELLFRE_SMITHING_TABLE_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> HellfireSmithingTableBlockEntity.this.progress;
                    case 1 -> HellfireSmithingTableBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> HellfireSmithingTableBlockEntity.this.progress = pValue;
                    case 1 -> HellfireSmithingTableBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }


    public ItemStack getRenderStack() {
        if(inventory.getStackInSlot(OUTPUT_SLOT).isEmpty()) {
            return inventory.getStackInSlot(INPUT_SLOT);
        } else {
            return inventory.getStackInSlot(OUTPUT_SLOT);
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();

        lazyItemHandler = LazyOptional.of( () -> inventory );
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }


    public void drops() {
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for(int i = 0; i < inventory.getSlots(); i++) {
            inv.setItem(i, inventory.getStackInSlot(i));
        }

        Containers.dropContents(level, worldPosition, inv);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.winferno.hellfire_smithing_table");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new HellfireSmithingTableMenu(pContainerId, pPlayerInventory, this, data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {

        pTag.put("inventory", inventory.serializeNBT());
        pTag.putInt("hellfire_smithing_table.progress", progress);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        inventory.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("hellfire_smithing_table.progress");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if(hasRecipe()) {
            increaseCraftingProgress();
            setChanged(pLevel, pPos, pState);

            ModMessages.sendToClient(new HellforgeUpdatePacket(pPos, true), pLevel, pPos);


            if(hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {

        Optional<HellforgingRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);

        this.inventory.extractItem(INPUT_SLOT, 1, false);


        this.inventory.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                this.inventory.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        Optional<HellforgingRecipe> recipe = getCurrentRecipe();

        if(recipe.isEmpty()) {
            return false;
        }

        ItemStack result = recipe.get().getResultItem(null);

        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<HellforgingRecipe> getCurrentRecipe() {
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for(int i = 0; i < inventory.getSlots(); i++) {
            inv.setItem(i, inventory.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(HellforgingRecipe.Type.INSTANCE, inv, this.level);
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.inventory.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.inventory.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.inventory.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.inventory.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }


    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
}
