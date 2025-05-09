package fr.inferno.block;

import fr.inferno.block.custom.HellfireSmithingTableBlock;
import fr.inferno.block.custom.ModPortalBlock;
import fr.inferno.commons.Commons;
import fr.inferno.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Commons.MOD_ID);




    public static final RegistryObject<Block> HELLFORGED_IRON_BLOCK = registerBlock("hellforged_iron_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(50.0F, 1200.0F)));

    public static final RegistryObject<Block> HELLFORGED_IRON_ORE = registerBlock("hellforged_iron_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).strength(50.0F, 1200.0F), UniformInt.of(3, 6)));

    public static final RegistryObject<Block> RAW_HELLFORGED_IRON_BLOCK = registerBlock("raw_hellforged_iron_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK).strength(50.0F, 1200.0F)));


    public static final RegistryObject<Block> HELLFIRE_SMITHING_TABLE = registerBlock("hellfire_smithing_table",
            () -> new HellfireSmithingTableBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .strength(50.0F, 1200.0F)));

    public static final RegistryObject<Block> HELL_GATE = registerBlock("hell_gate",
            () -> new ModPortalBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(50.0F, 1200.0F)));


    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> regBlock = BLOCKS.register(name, block);
        registerBlockItem(name, regBlock);
        return regBlock;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
