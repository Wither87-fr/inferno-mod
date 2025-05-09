package fr.inferno.packet.handlers;

import fr.inferno.event.ForgeClientEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class TeleportingStatusPacket {
    private final boolean isTeleporting;
    private final UUID playerID;

    public TeleportingStatusPacket(FriendlyByteBuf buf) {
        this.isTeleporting = buf.readBoolean();
        this.playerID = buf.readUUID();
    }

    public TeleportingStatusPacket(boolean isTeleporting, UUID uuid) {
        this.isTeleporting = isTeleporting;
        this.playerID = uuid;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(isTeleporting);
        buf.writeUUID(playerID);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Côté client uniquement
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> this::handleClient);
        });
        context.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private void handleClient() {
        if(isTeleporting) {
            ForgeClientEvents.addPentaclePosition(playerID);
        } else {
            ForgeClientEvents.removePentaclePosition(playerID);
        }
    }

}
