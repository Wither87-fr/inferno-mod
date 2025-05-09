package fr.inferno.event;

import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeleportingPlayersData {
    private static final Map<UUID, Vec3> teleportingPlayers = new HashMap<>();

    public static void addPlayer(UUID playerID, Vec3 position) {
        teleportingPlayers.put(playerID, position);
    }

    public static void removePlayer(UUID playerID) {
        teleportingPlayers.remove(playerID);
    }

    public static Map<UUID, Vec3> getTeleportingPlayers() {
        return teleportingPlayers;
    }

    public static void updatePlayerPosition(UUID playerID, Vec3 newPosition) {
        if (teleportingPlayers.containsKey(playerID)) {
            teleportingPlayers.put(playerID, newPosition);
        }
    }

}
