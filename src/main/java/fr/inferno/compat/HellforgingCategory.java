package fr.inferno.compat;

import fr.inferno.block.ModBlocks;
import fr.inferno.commons.Commons;
import fr.inferno.item.ModItems;
import fr.inferno.recipe.HellforgingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class HellforgingCategory implements IRecipeCategory<HellforgingRecipe> {


    public static final ResourceLocation UID = new ResourceLocation(Commons.MOD_ID, "hellforging");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Commons.MOD_ID, "textures/gui/hellfire_smithing_table_gui.png");

    public static final RecipeType<HellforgingRecipe> HELLFORGING_TYPE =
            new RecipeType<>(UID, HellforgingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public HellforgingCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.HELLFIRE_SMITHING_TABLE.get()));
    }


    @Override
    public RecipeType<HellforgingRecipe> getRecipeType() {
        return HELLFORGING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.winferno.hellfire_smithing_table");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, HellforgingRecipe hellforgingRecipe, IFocusGroup iFocusGroup) {
        iRecipeLayoutBuilder.addSlot(
                RecipeIngredientRole.INPUT, 80, 11
        ).addIngredients(hellforgingRecipe.getIngredients().get(0));


        iRecipeLayoutBuilder.addSlot(
                RecipeIngredientRole.OUTPUT, 80, 59
        ).addItemStack(hellforgingRecipe.getResultItem(null));
    }
}
