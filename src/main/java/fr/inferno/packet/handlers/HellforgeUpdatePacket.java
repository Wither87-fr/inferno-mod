package fr.inferno.packet.handlers;

import fr.inferno.block.entity.HellfireSmithingTableBlockEntity;
import fr.inferno.particles.ParticlesHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.awt.*;
import java.util.function.Supplier;

public class HellforgeUpdatePacket {

    private final BlockPos pos;
    private final boolean isProcessing;

    public HellforgeUpdatePacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.isProcessing = buf.readBoolean();
    }

    public HellforgeUpdatePacket(BlockPos pos, boolean isProcessing) {
        this.pos = pos;
        this.isProcessing = isProcessing;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeBoolean(isProcessing);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> this::handleClient);

        });
        return true;
    }


    @OnlyIn(Dist.CLIENT)
    private void handleClient() {
        Level level = Minecraft.getInstance().level;
        if (level != null) {
            if (level.getBlockEntity(pos) instanceof HellfireSmithingTableBlockEntity) {
                if (isProcessing) {
                    Vec3 particlePos = new Vec3(pos.getX() + 0.5, pos.getY() + 0.77, pos.getZ() + 0.5);
                    Vec3 northPos = new Vec3(pos.getX() + 0.5, pos.getY() + 0.85, pos.getZ() + 0.8);
                    Vec3 southPos = new Vec3(pos.getX() + 0.5, pos.getY() + 0.85, pos.getZ() + 0.2);
                    Vec3 eastPos = new Vec3(pos.getX() + 0.8, pos.getY() + 0.85, pos.getZ() + 0.5);
                    Vec3 westPos = new Vec3(pos.getX() + 0.2, pos.getY() + 0.85, pos.getZ() + 0.5);
                    ParticlesHelper.createFocusedEnergyRayon(level, particlePos, northPos, southPos, eastPos, westPos, Color.RED, Color.ORANGE);
                }
            }
        }
    }

}
