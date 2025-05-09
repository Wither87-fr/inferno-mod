package fr.inferno.datagen;

import fr.inferno.block.ModBlocks;
import fr.inferno.commons.Commons;
import fr.inferno.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    private static final List<ItemLike> HELLFORGED_IRON_SMELTABLES = List.of(
            ModItems.RAW_HELLFORGED_IRON.get(),
            ModBlocks.HELLFORGED_IRON_ORE.get()
    );

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        oreBlasting(consumer, HELLFORGED_IRON_SMELTABLES, RecipeCategory.MISC, ModItems.HELLFORGED_IRON.get(), 0.1F, 9000, "hellforged_iron");


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.HELLFORGED_IRON_BLOCK.get())
                .pattern("HHH")
                .pattern("HHH")
                .pattern("HHH")
                .define('H', ModItems.HELLFORGED_IRON.get())
                .unlockedBy(getHasName(ModItems.HELLFORGED_IRON.get()), has(ModItems.HELLFORGED_IRON.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.RAW_HELLFORGED_IRON_BLOCK.get())
                .pattern("RRR")
                .pattern("RRR")
                .pattern("RRR")
                .define('R', ModItems.RAW_HELLFORGED_IRON.get())
                .unlockedBy(getHasName(ModItems.RAW_HELLFORGED_IRON.get()), has(ModItems.RAW_HELLFORGED_IRON.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HELLFORGED_SHOVEL.get())
                .pattern("H")
                .pattern("S")
                .pattern("S")
                .define('H', ModBlocks.HELLFORGED_IRON_BLOCK.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.HELLFORGED_IRON_BLOCK.get()), has(ModBlocks.HELLFORGED_IRON_BLOCK.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HELLFORGED_PICKAXE.get())
                .pattern("HHH")
                .pattern(" S ")
                .pattern(" S ")
                .define('H', ModBlocks.HELLFORGED_IRON_BLOCK.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.HELLFORGED_IRON_BLOCK.get()), has(ModBlocks.HELLFORGED_IRON_BLOCK.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HELLFORGED_AXE.get())
                .pattern("HH")
                .pattern("HS")
                .pattern(" S")
                .define('H', ModBlocks.HELLFORGED_IRON_BLOCK.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.HELLFORGED_IRON_BLOCK.get()), has(ModBlocks.HELLFORGED_IRON_BLOCK.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HELLFORGED_AXE.get())
                .pattern("HH")
                .pattern("SH")
                .pattern("S ")
                .define('H', ModBlocks.HELLFORGED_IRON_BLOCK.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.HELLFORGED_IRON_BLOCK.get()), has(ModBlocks.HELLFORGED_IRON_BLOCK.get()))
                .save(consumer, Commons.MOD_ID + ":hellforged_axe_mirrored");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HELLFORGED_HOE.get())
                .pattern("HH")
                .pattern(" S")
                .pattern(" S")
                .define('H', ModBlocks.HELLFORGED_IRON_BLOCK.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.HELLFORGED_IRON_BLOCK.get()), has(ModBlocks.HELLFORGED_IRON_BLOCK.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HELLFORGED_HOE.get())
                .pattern("HH")
                .pattern("S ")
                .pattern("S ")
                .define('H', ModBlocks.HELLFORGED_IRON_BLOCK.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.HELLFORGED_IRON_BLOCK.get()), has(ModBlocks.HELLFORGED_IRON_BLOCK.get()))
                .save(consumer, Commons.MOD_ID + ":hellforged_hoe_mirrored");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HELLFORGED_SWORD.get())
                .pattern("H")
                .pattern("H")
                .pattern("S")
                .define('H', ModBlocks.HELLFORGED_IRON_BLOCK.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.HELLFORGED_IRON_BLOCK.get()), has(ModBlocks.HELLFORGED_IRON_BLOCK.get()))
                .save(consumer);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HELLFORGED_HELMET.get())
                .pattern("HHH")
                .pattern("H H")
                .define('H', ModBlocks.HELLFORGED_IRON_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.HELLFORGED_IRON_BLOCK.get()), has(ModBlocks.HELLFORGED_IRON_BLOCK.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HELLFORGED_CHESTPLATE.get())
                .pattern("H H")
                .pattern("HHH")
                .pattern("HHH")
                .define('H', ModBlocks.HELLFORGED_IRON_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.HELLFORGED_IRON_BLOCK.get()), has(ModBlocks.HELLFORGED_IRON_BLOCK.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HELLFORGED_LEGGINGS.get())
                .pattern("HHH")
                .pattern("H H")
                .pattern("H H")
                .define('H', ModBlocks.HELLFORGED_IRON_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.HELLFORGED_IRON_BLOCK.get()), has(ModBlocks.HELLFORGED_IRON_BLOCK.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HELLFORGED_BOOTS.get())
                .pattern("H H")
                .pattern("H H")
                .define('H', ModBlocks.HELLFORGED_IRON_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.HELLFORGED_IRON_BLOCK.get()), has(ModBlocks.HELLFORGED_IRON_BLOCK.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.HELLFIRE_SMITHING_TABLE.get())
                .pattern("LNL")
                .pattern("NHN")
                .pattern("LNL")
                .define('H', ModBlocks.HELLFORGED_IRON_BLOCK.get())
                .define('L', Items.LAVA_BUCKET)
                .define('N', Items.NETHER_STAR)
                .unlockedBy(getHasName(ModBlocks.HELLFORGED_IRON_BLOCK.get()), has(ModBlocks.HELLFORGED_IRON_BLOCK.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.HELL_GATE.get())
                .pattern("ORO")
                .pattern("PEP")
                .pattern("ORO")
                .define('O', Blocks.OBSIDIAN)
                .define('R', Items.BLAZE_ROD)
                .define('P', Items.BLAZE_POWDER)
                .define('E', Items.ENDER_EYE)
                .unlockedBy(getHasName(ModBlocks.HELLFORGED_IRON_BLOCK.get()), has(ModBlocks.HELLFORGED_IRON_BLOCK.get()))
                .save(consumer);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.HELLFORGED_IRON.get(), 9)
                .requires(ModBlocks.HELLFORGED_IRON_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.HELLFORGED_IRON_BLOCK.get()), has(ModBlocks.HELLFORGED_IRON_BLOCK.get()))
                .save(consumer);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RAW_HELLFORGED_IRON.get(), 9)
                .requires(ModBlocks.RAW_HELLFORGED_IRON_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.RAW_HELLFORGED_IRON_BLOCK.get()), has(ModBlocks.RAW_HELLFORGED_IRON_BLOCK.get()))
                .save(consumer);
    }


    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer,  Commons.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }

    }
}
