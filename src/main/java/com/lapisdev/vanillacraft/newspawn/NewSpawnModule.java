package com.lapisdev.vanillacraft.newspawn;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import static com.lapisdev.vanillacraft.VanillacraftPlugin.handle;

public class NewSpawnModule {
    public NewSpawnModule() {
        handle(new NewPlayerJoinListener());;
    }

    public static void setSpawnImmediate(ServerPlayer player, Location spawn) {
        Player mcPlayer = Bukkit.getPlayer(player.minecraftUuid);
        mcPlayer.setRespawnLocation(spawn);
        mcPlayer.teleportAsync(spawn);
    }
}
