package fr.inferno.block.custom;

import fr.inferno.commons.Commons;
import fr.inferno.mod.InfernoMod;
import fr.inferno.packet.ModMessages;
import fr.inferno.packet.handlers.TeleportingStatusPacket;
import fr.inferno.worldgen.dimension.ModDimensions;
import fr.inferno.worldgen.portal.ModTeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class ModPortalBlock extends Block {
    public ModPortalBlock(Properties pProperties) {
        super(pProperties);
    }

    private static int teleportationCounter = 0;
    private static final int DELAY_TICKS = 5 * Commons.TICKS_PER_SECOND;
    private static BlockPos targetPos;
    private static Player targetPlayer;





    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer.canChangeDimensions()) {
            if (!pLevel.isClientSide()) {
                ModMessages.sendToClient(new TeleportingStatusPacket(true, pPlayer.getUUID()), pPlayer.level(), pPlayer.blockPosition());
                teleportationCounter = DELAY_TICKS;
                targetPos = pPos;
                targetPlayer = pPlayer;
                pPlayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 120, 2));
                pPlayer.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 220, 3));

                MinecraftForge.EVENT_BUS.register(new Object() {
                    @SubscribeEvent
                    public void onServerTick(TickEvent.ServerTickEvent event) {
                        if (event.phase == TickEvent.Phase.END) {
                            if (teleportationCounter > 0) {
                                teleportationCounter--;
                                if (teleportationCounter == 0) {
                                    handleHellGate(targetPlayer, targetPos);
                                    // Ajouter un délai de 3 secondes (60 ticks) avant de mettre le statut à false
                                    teleportationCounter = 3 * Commons.TICKS_PER_SECOND;
                                    MinecraftForge.EVENT_BUS.register(new Object() {
                                        private int statusDelay = 3 * Commons.TICKS_PER_SECOND;

                                        @SubscribeEvent
                                        public void onDelayedStatusUpdate(TickEvent.ServerTickEvent e) {
                                            if (e.phase == TickEvent.Phase.END) {
                                                if (statusDelay > 0) {
                                                    statusDelay--;
                                                    if (statusDelay == 0) {
                                                        ModMessages.sendToClient(new TeleportingStatusPacket(false, pPlayer.getUUID()), pPlayer.level(), pPlayer.blockPosition());
                                                        MinecraftForge.EVENT_BUS.unregister(this);
                                                    }
                                                }
                                            }
                                        }
                                    });
                                    MinecraftForge.EVENT_BUS.unregister(this);
                                }
                            }
                        }

                    }
                });
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.CONSUME;

    }

    private void handleHellGate(Entity player, BlockPos pPos) {
        if (player.level() instanceof ServerLevel serverlevel) {
            MinecraftServer minecraftserver = serverlevel.getServer();
            ResourceKey<Level> resourcekey = player.level().dimension() == ModDimensions.HELL_LEVEL_KEY ?
                    Level.OVERWORLD : ModDimensions.HELL_LEVEL_KEY;

            ServerLevel portalDimension = minecraftserver.getLevel(resourcekey);
            if (portalDimension != null && !player.isPassenger()) {
                if (resourcekey == ModDimensions.HELL_LEVEL_KEY) {
                    player.changeDimension(portalDimension, new ModTeleporter(pPos, true));
                    ModMessages.sendToClient(new TeleportingStatusPacket(true, player.getUUID()), player.level(), player.blockPosition());
                } else {
                    player.changeDimension(portalDimension, new ModTeleporter(pPos, false));
                    ModMessages.sendToClient(new TeleportingStatusPacket(true, player.getUUID()), player.level(), player.blockPosition());
                }
            }
        }
    }
}

