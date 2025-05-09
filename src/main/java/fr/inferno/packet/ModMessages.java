package fr.inferno.packet;

import fr.inferno.commons.Commons;
import fr.inferno.packet.handlers.DistributePentaclePacket;
import fr.inferno.packet.handlers.HellforgeUpdatePacket;
import fr.inferno.packet.handlers.TeleportingStatusPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Commons.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();


        INSTANCE = net;


        net.messageBuilder(HellforgeUpdatePacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(HellforgeUpdatePacket::new)
                .encoder(HellforgeUpdatePacket::toBytes)
                .consumerMainThread(HellforgeUpdatePacket::handle)
                .add();

        net.messageBuilder(TeleportingStatusPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(TeleportingStatusPacket::new)
                .encoder(TeleportingStatusPacket::toBytes)
                .consumerMainThread(TeleportingStatusPacket::handle)
                .add();

        net.messageBuilder(DistributePentaclePacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(DistributePentaclePacket::new)
                .encoder(DistributePentaclePacket::toBytes)
                .consumerMainThread(DistributePentaclePacket::handle)
                .add();

    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }


    public static <MSG> void sendToClient(MSG message, Level level, BlockPos pos) {
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() ->
                level.getChunkAt(pos)), message);

    }
}
