package fr.inferno.worldgen.biome.surface;

import fr.inferno.worldgen.biome.ModBiomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource NETHERRACK = makeStateRule(Blocks.NETHERRACK);
    private static final SurfaceRules.RuleSource NYLIUM = makeStateRule(Blocks.CRIMSON_NYLIUM);
    private static final SurfaceRules.RuleSource OBSIDIAN = makeStateRule(Blocks.OBSIDIAN);
    private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);



    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);

        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, NYLIUM), NETHERRACK);

        return SurfaceRules.sequence(
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(ModBiomes.VOLCANO_BIOME),
                                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, NYLIUM)
                        ),
                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(ModBiomes.VOLCANO_BIOME),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)),
                                        BEDROCK)
                        ),
                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(ModBiomes.VOLCANO_BIOME),
                                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, NETHERRACK)
                        ),
                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(ModBiomes.VOLCANO_BIOME),
                                SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, OBSIDIAN)
                        ),
                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(ModBiomes.VOLCANO_BIOME),
                                SurfaceRules.ifTrue(SurfaceRules.VERY_DEEP_UNDER_FLOOR, OBSIDIAN)
                        ),
                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(ModBiomes.VOLCANO_BIOME),
                                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface)
                        ),
                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(ModBiomes.VOLCANO_BIOME),
                                SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, OBSIDIAN)
                        ),
                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(ModBiomes.VOLCANO_BIOME),
                                SurfaceRules.ifTrue(SurfaceRules.UNDER_CEILING, OBSIDIAN)
                        ),
                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(ModBiomes.VOLCANO_BIOME),
                                SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0, true, 200, CaveSurface.FLOOR)
                                        , OBSIDIAN)
                        ),
                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(ModBiomes.VOLCANO_BIOME),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
                                        OBSIDIAN
                                )
                        )
                ));
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
