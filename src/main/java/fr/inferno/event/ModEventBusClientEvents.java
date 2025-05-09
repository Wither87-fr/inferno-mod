package fr.inferno.event;


import fr.inferno.block.entity.ModBlockEntities;
import fr.inferno.block.entity.renderer.HellfireSmithingTableRenderer;
import fr.inferno.commons.Commons;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Commons.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.HELLFRE_SMITHING_TABLE_BE.get(), HellfireSmithingTableRenderer::new);
    }
}
