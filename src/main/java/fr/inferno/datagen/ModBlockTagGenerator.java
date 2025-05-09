package fr.inferno.datagen;

import fr.inferno.block.ModBlocks;
import fr.inferno.commons.Commons;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {


    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Commons.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // custom tags here
        // this.tag(ModTags.Blocks.NEEDS_HELLFORGED_TOOL).add(ModBlocks.HELLO_BLOCK.get());


        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(
                        ModBlocks.HELLFORGED_IRON_ORE.get(),
                        ModBlocks.HELLFORGED_IRON_BLOCK.get(),
                        ModBlocks.RAW_HELLFORGED_IRON_BLOCK.get(),
                        ModBlocks.HELLFIRE_SMITHING_TABLE.get()
                );


        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(
                        ModBlocks.HELLFORGED_IRON_ORE.get(),
                        ModBlocks.HELLFORGED_IRON_BLOCK.get(),
                        ModBlocks.RAW_HELLFORGED_IRON_BLOCK.get(),
                        ModBlocks.HELLFIRE_SMITHING_TABLE.get()
                );

    }
}
