package fr.inferno.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import fr.inferno.commons.Commons;
import fr.inferno.mod.InfernoMod;
import fr.inferno.particles.ParticlesHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Commons.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeClientEvents {

    private static final Set<UUID> teleportingPlayers = new HashSet<>();

    public static void addPentaclePosition(UUID uuid) {
        teleportingPlayers.add(uuid);
    }

    // Méthode pour retirer une position
    public static void removePentaclePosition(UUID uuid) {
        teleportingPlayers.remove(uuid);
    }


    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        Level level = minecraft.level;
        if (level != null) {
            // Parcourir toutes les positions enregistrées
            teleportingPlayers.forEach(player -> {
                var playerEntity = minecraft.level.getPlayerByUUID(player);
                if (playerEntity != null) {
                    Vec3 position = playerEntity.position();
                    ParticlesHelper.createRotatingPentacle(level, position, Color.RED);
                }
            });
        }

    }

    @SubscribeEvent
    public static void onPlayerMove(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof LocalPlayer player) {
            if (teleportingPlayers.contains(player.getUUID())) {
                Vec3 motion = player.getDeltaMovement();
                InfernoMod.LOGGER.info("Motion : " + motion);
                // Permet la chute mais bloque les mouvements horizontaux
                player.setDeltaMovement(0, motion.y, 0);
                // Annule l'événement de mouvement
                event.setCanceled(true);
                // set position to only the y dostance
                player.setPos(player.getX(), player.getY() + motion.y, player.getZ());

            }
        }
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getEntity() instanceof LocalPlayer player) {
            if (teleportingPlayers.contains(player.getUUID())) {
                event.setCanceled(true);
            }
        }
    }


    @SubscribeEvent
    public static void onRenderGui(RenderGuiOverlayEvent.Post event) {

        final LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && player.getPersistentData().getBoolean("isTeleporting")) {
            GuiGraphics guiGraphics = event.getGuiGraphics();
            PoseStack poseStack = guiGraphics.pose();

            // Sauvegarder l'état actuel
            poseStack.pushPose();

            // Configurer le rendu
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);

            // Créer le buffer pour le rendu
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tesselator.getBuilder();

            // Dessiner un rectangle rouge semi-transparent
            float alpha = 0.01f; // 20% d'opacité
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            Matrix4f matrix = poseStack.last().pose();
            bufferbuilder.vertex(matrix, 0, 0, 0).color(1f, 0f, 0f, alpha).endVertex();
            bufferbuilder.vertex(matrix, 0, event.getWindow().getGuiScaledHeight(), 0).color(1f, 0f, 0f, alpha).endVertex();
            bufferbuilder.vertex(matrix, event.getWindow().getGuiScaledWidth(), event.getWindow().getGuiScaledHeight(), 0).color(1f, 0f, 0f, alpha).endVertex();
            bufferbuilder.vertex(matrix, event.getWindow().getGuiScaledWidth(), 0, 0).color(1f, 0f, 0f, alpha).endVertex();
            tesselator.end();

            // Restaurer l'état
            RenderSystem.disableBlend();
            poseStack.popPose();
        }
    }

}
