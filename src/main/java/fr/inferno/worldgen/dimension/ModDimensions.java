package fr.inferno.worldgen.dimension;

import com.mojang.datafixers.util.Pair;
import fr.inferno.commons.Commons;
import fr.inferno.worldgen.biome.ModBiomes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import java.util.List;
import java.util.OptionalLong;

public class ModDimensions {

    public static final ResourceKey<LevelStem> HELL_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(Commons.MOD_ID, "hell"));
    public static final ResourceKey<Level> HELL_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(Commons.MOD_ID, "hell"));
    public static final ResourceKey<DimensionType> HELL_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(Commons.MOD_ID, "hell_type"));


    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(HELL_TYPE, new DimensionType(
                OptionalLong.of(18000), // fixedTime
                false, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                false, // natural
                16.0, // coordinateScale
                false, // bedWorks
                false, // respawnAnchorWorks
                0, // minY
                256, // height
                256, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                BuiltinDimensionTypes.OVERWORLD_EFFECTS, // effectsLocation
                1.0f, // ambientLight
                new DimensionType.MonsterSettings(true, false, ConstantInt.of(0), 15)));
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        NoiseBasedChunkGenerator wrappedChunkGenerator = new NoiseBasedChunkGenerator(
                new FixedBiomeSource(biomeRegistry.getOrThrow(ModBiomes.VOLCANO_BIOME)),
                noiseGenSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD));



        LevelStem stem = new LevelStem(dimTypes.getOrThrow(ModDimensions.HELL_TYPE), wrappedChunkGenerator);

        context.register(HELL_KEY, stem);
    }

}
