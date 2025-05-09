package fr.inferno.datagen;

import fr.inferno.block.ModBlocks;
import fr.inferno.commons.Commons;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {


    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Commons.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.HELLFORGED_IRON_BLOCK);
        blockWithItem(ModBlocks.RAW_HELLFORGED_IRON_BLOCK);
        blockWithItem(ModBlocks.HELLFORGED_IRON_ORE);
        blockWithItem(ModBlocks.HELL_GATE);

        simpleBlockWithItem(ModBlocks.HELLFIRE_SMITHING_TABLE.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/hellfire_smithing_table"))
        );
    }

    public void blockWithItem(RegistryObject<Block> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }
}
