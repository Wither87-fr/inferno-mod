package fr.inferno.block.entity;

import fr.inferno.block.ModBlocks;
import fr.inferno.commons.Commons;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Commons.MOD_ID);


    public static final RegistryObject<BlockEntityType<HellfireSmithingTableBlockEntity>> HELLFRE_SMITHING_TABLE_BE =
            BLOCK_ENTITIES.register("hellfire_smithing_table_be", () ->
                    BlockEntityType.Builder.of(HellfireSmithingTableBlockEntity::new, ModBlocks.HELLFIRE_SMITHING_TABLE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
