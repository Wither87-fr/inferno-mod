package fr.inferno.utils;

import fr.inferno.commons.Commons;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

import static net.minecraft.tags.TagEntry.tag;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_HELLFORGED_TOOL = tag("needs_hellforged_tools");

        private static TagKey<Block> tag(String name ) {
            return BlockTags.create(new ResourceLocation(Commons.MOD_ID, name));
        }
    }

    public static class Items {
        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(Commons.MOD_ID, name));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> IS_INFERNAL = tag("is_infernal");


        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registries.BIOME, new ResourceLocation(Commons.MOD_ID, name));
        }
    }

}
