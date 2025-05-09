package fr.inferno.compat;

import fr.inferno.commons.Commons;
import fr.inferno.recipe.HellforgingRecipe;
import fr.inferno.screen.HellfireSmithingTableScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIWitherInfernoPlugin  implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Commons.MOD_ID, "jei_plugin");
    }


    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new HellforgingCategory(registration.getJeiHelpers().getGuiHelper()));
    }


    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        List<HellforgingRecipe> hellforgingRecipes = recipeManager.getAllRecipesFor(HellforgingRecipe.Type.INSTANCE);
        registration.addRecipes(HellforgingCategory.HELLFORGING_TYPE, hellforgingRecipes);
    }


    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(HellfireSmithingTableScreen.class, 70, 30, 20, 25, HellforgingCategory.HELLFORGING_TYPE);
    }
}
