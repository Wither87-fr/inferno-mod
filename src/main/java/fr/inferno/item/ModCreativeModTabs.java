package fr.inferno.item;

import fr.inferno.block.ModBlocks;
import fr.inferno.commons.Commons;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Commons.MOD_ID );


    public static final RegistryObject<CreativeModeTab> WITHER_INFERNO = CREATIVE_MOD_TABS.register("wither_inferno",
            () ->  CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.HELLFORGED_IRON.get()))
                    .title(Component.translatable("creativetab.winferno.wither_inferno"))
                    .displayItems( (itemDisplayParameters, output) ->  {
                        output.accept(ModItems.RAW_HELLFORGED_IRON.get());
                        output.accept(ModItems.HELLFORGED_IRON.get());
                        output.accept(ModBlocks.HELLFORGED_IRON_BLOCK.get());
                        output.accept(ModBlocks.HELLFORGED_IRON_ORE.get());
                        output.accept(ModBlocks.RAW_HELLFORGED_IRON_BLOCK.get());
                        output.accept(ModItems.HELLFORGED_SWORD.get());
                        output.accept(ModItems.HELLFORGED_AXE.get());
                        output.accept(ModItems.HELLFORGED_HOE.get());
                        output.accept(ModItems.HELLFORGED_PICKAXE.get());
                        output.accept(ModItems.HELLFORGED_SHOVEL.get());
                        output.accept(ModItems.HELLFORGED_HELMET.get());
                        output.accept(ModItems.HELLFORGED_CHESTPLATE.get());
                        output.accept(ModItems.HELLFORGED_LEGGINGS.get());
                        output.accept(ModItems.HELLFORGED_BOOTS.get());
                        output.accept(ModBlocks.HELLFIRE_SMITHING_TABLE.get());
                        output.accept(ModBlocks.HELL_GATE.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MOD_TABS.register(eventBus);
    }
}
