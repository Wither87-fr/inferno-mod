package fr.inferno.recipe;

import fr.inferno.commons.Commons;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipies {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Commons.MOD_ID);


    public static final RegistryObject<RecipeSerializer<HellforgingRecipe>> HELLFORGING_SERIALIZER =
            SERIALIZERS.register("hellforging", () -> HellforgingRecipe.Serializer.INSTANCE);


    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
