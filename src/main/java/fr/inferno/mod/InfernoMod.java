package fr.inferno.mod;

import com.mojang.logging.LogUtils;
import fr.inferno.block.ModBlocks;
import fr.inferno.block.entity.ModBlockEntities;
import fr.inferno.commons.Commons;
import fr.inferno.item.ModCreativeModTabs;
import fr.inferno.item.ModItems;
import fr.inferno.packet.ModMessages;
import fr.inferno.recipe.ModRecipies;
import fr.inferno.screen.HellfireSmithingTableMenu;
import fr.inferno.screen.HellfireSmithingTableScreen;
import fr.inferno.screen.ModMenuTypes;
import fr.inferno.worldgen.biome.ModTerraBlender;
import fr.inferno.worldgen.biome.surface.ModSurfaceRules;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import terrablender.api.SurfaceRuleManager;

@Mod(Commons.MOD_ID)
public class InfernoMod
{

    public static final Logger LOGGER = LogUtils.getLogger();

    public InfernoMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModMessages.register();

        ModCreativeModTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModRecipies.register(modEventBus);
        ModTerraBlender.registerBiomes();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, Commons.MOD_ID, ModSurfaceRules.makeRules());
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    @Mod.EventBusSubscriber(modid = Commons.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            MenuScreens.register(ModMenuTypes.HELLFIRE_SMITHING_TABLE_MENU.get(), HellfireSmithingTableScreen::new);
        }
    }
}
