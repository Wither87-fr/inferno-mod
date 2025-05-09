package fr.inferno.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.inferno.commons.Commons;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class HellforgingRecipe implements Recipe<SimpleContainer> {

    private final NonNullList<Ingredient> inputItems;
    private final ItemStack outputItem;
    private final ResourceLocation id;

    public HellforgingRecipe(NonNullList<Ingredient> inputItems, ItemStack outputItem, ResourceLocation id) {
        this.inputItems = inputItems;
        this.outputItem = outputItem;
        this.id = id;
    }


    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputItems;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {

        if(pLevel.isClientSide()) {
            return false;
        }



        return inputItems.get(0) // from recipe
                .test(
                        pContainer.getItem(0) // from container
                );
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return outputItem.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return outputItem.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<HellforgingRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "hellforging";
    }

    public static class Serializer implements RecipeSerializer<HellforgingRecipe> {

        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Commons.MOD_ID, "hellforging");

        @Override
        public HellforgingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack outputItem = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");

            NonNullList<Ingredient> inputItems = NonNullList.withSize(1, Ingredient.EMPTY);

            for(int i = 0; i < ingredients.size(); i++) {
                inputItems.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new HellforgingRecipe(inputItems, outputItem, pRecipeId);
        }

        @Override
        public @Nullable HellforgingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {

            NonNullList<Ingredient> inputItems = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputItems.size(); i++) {
                inputItems.set(i, Ingredient.fromNetwork(pBuffer));
            }
            ItemStack output = pBuffer.readItem();
            return new HellforgingRecipe(inputItems, output, pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, HellforgingRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.inputItems.size());

            for(Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
        }
    }
}
