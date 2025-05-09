package fr.inferno.datagen;

import fr.inferno.commons.Commons;
import fr.inferno.utils.ModTags;
import fr.inferno.worldgen.biome.ModBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagGenerator extends BiomeTagsProvider {
    public ModBiomeTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, Commons.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
       // this.tag(ModTags.Biomes.IS_INFERNAL).add(ModBiomes.VOLCANO_BIOME);
       this.tag(ModTags.Biomes.IS_INFERNAL).addOptional(new ResourceLocation(Commons.MOD_ID, "volcano_biome"));
    }
}
