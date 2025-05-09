package fr.inferno.item;

import fr.inferno.commons.Commons;
import fr.inferno.item.custom.ModArmorItem;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Commons.MOD_ID);

    public static final RegistryObject<Item> HELLFORGED_IRON = ITEMS.register("hellforged_iron",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_HELLFORGED_IRON = ITEMS.register("raw_hellforged_iron",
            () -> new Item(new Item.Properties()));


    public static final RegistryObject<Item> HELLFORGED_SWORD = ITEMS.register("hellforged_sword",
            () -> new SwordItem(ModToolTiers.HELLFORGED, 4, 2, new Item.Properties()));

    public static final RegistryObject<Item> HELLFORGED_PICKAXE = ITEMS.register("hellforged_pickaxe",
            () -> new PickaxeItem(ModToolTiers.HELLFORGED, 1, 1, new Item.Properties()));

    public static final RegistryObject<Item> HELLFORGED_AXE = ITEMS.register("hellforged_axe",
            () -> new AxeItem(ModToolTiers.HELLFORGED, 7, 1, new Item.Properties()));

    public static final RegistryObject<Item> HELLFORGED_SHOVEL = ITEMS.register("hellforged_shovel",
            () -> new ShovelItem(ModToolTiers.HELLFORGED, 0, 0, new Item.Properties()));

    public static final RegistryObject<Item> HELLFORGED_HOE = ITEMS.register("hellforged_hoe",
            () -> new HoeItem(ModToolTiers.HELLFORGED, 0, 0, new Item.Properties()));


    public static final RegistryObject<Item> HELLFORGED_HELMET = ITEMS.register("hellforged_helmet",
            () -> new ModArmorItem(ModArmorMaterials.HELLFORGED, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> HELLFORGED_CHESTPLATE = ITEMS.register("hellforged_chestplate",
            () -> new ModArmorItem(ModArmorMaterials.HELLFORGED, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> HELLFORGED_LEGGINGS = ITEMS.register("hellforged_leggings",
            () -> new ModArmorItem(ModArmorMaterials.HELLFORGED, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> HELLFORGED_BOOTS = ITEMS.register("hellforged_boots",
            () -> new ModArmorItem(ModArmorMaterials.HELLFORGED, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
