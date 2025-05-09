package fr.inferno.item;

import fr.inferno.commons.Commons;
import fr.inferno.utils.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {
    public static final Tier HELLFORGED = TierSortingRegistry.registerTier(
            new ForgeTier(5, 666666, 666, 666, 666,
                    ModTags.Blocks.NEEDS_HELLFORGED_TOOL, () -> Ingredient.of(ModItems.HELLFORGED_IRON.get())),
            new ResourceLocation(Commons.MOD_ID, "hellforged"), List.of(Tiers.NETHERITE), List.of()
    );
}
