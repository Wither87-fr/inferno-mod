package fr.inferno.packet.handlers;

import fr.inferno.block.entity.HellfireSmithingTableBlockEntity;
import fr.inferno.particles.ParticlesHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.awt.*;
import java.util.function.Supplier;

public class DistributePentaclePacket {
    private final Vec3 pos;
    private final boolean isProcessing;

    public DistributePentaclePacket(FriendlyByteBuf buf) {
        this.pos = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        this.isProcessing = buf.readBoolean();
    }

    public DistributePentaclePacket(Vec3 pos, boolean isProcessing) {
        this.pos = pos;
        this.isProcessing = isProcessing;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(pos.x);
        buf.writeDouble(pos.y);
        buf.writeDouble(pos.z);
        buf.writeBoolean(isProcessing);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> this::handleClient);
        });
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    private void handleClient() {
        Level level = Minecraft.getInstance().level;
        if (level != null) {
            if (isProcessing) {
                  ParticlesHelper.createRotatingPentacle(level, pos, Color.RED);
            }
        }
    }
}
