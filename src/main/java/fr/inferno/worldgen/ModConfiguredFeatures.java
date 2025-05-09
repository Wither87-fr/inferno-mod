package fr.inferno.worldgen;

import fr.inferno.block.ModBlocks;
import fr.inferno.commons.Commons;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.Tags;

import java.util.List;

public class ModConfiguredFeatures {

    public static ResourceKey<ConfiguredFeature<?, ?>> HELLFORGED_IRON_ORE_KEY = registerKey("hellforged_iron_ore");


    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {


        RuleTest obsidianReplacable = new TagMatchTest(Tags.Blocks.OBSIDIAN);

        List<OreConfiguration.TargetBlockState> ores = List.of(
                OreConfiguration.target(obsidianReplacable, ModBlocks.HELLFORGED_IRON_ORE.get().defaultBlockState())
        );

        register(context, HELLFORGED_IRON_ORE_KEY, Feature.ORE, new OreConfiguration(ores, 10));
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Commons.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?,?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC config) {
        context.register(key, new ConfiguredFeature<>(feature, config));
    }
}
