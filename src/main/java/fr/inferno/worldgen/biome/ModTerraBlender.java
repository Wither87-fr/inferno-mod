package fr.inferno.worldgen.biome;

import fr.inferno.commons.Commons;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;

public class ModTerraBlender {

    public static void registerBiomes() {
        Regions.register(new ModOverworldRegion(new ResourceLocation(Commons.MOD_ID, "overworld"), 5));
    }
}
