package com.lapisdev.vanillacraft.newspawn;

import com.lapisdev.vanillacraft.database.Query;
import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.region.PlayerRegion;
import com.lapisdev.vanillacraft.task.RunTask;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.ResultSet;

import static com.lapisdev.vanillacraft.newspawn.NewSpawnModule.setSpawnImmediate;

public class NewPlayerJoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        RunTask.async((_) -> {
            ServerPlayer player = ServerPlayer.fromMinecraftUuid(e.getPlayer().getUniqueId());
            if (player == null) {
                return; // they'll be kicked in a second, anyway.
            }
            if (hasPlayedBefore(player)) {
                return;
            }
            PlayerRegion playerRegion = PlayerRegion.fromPlayer(player);
            if (playerRegion == null) {
                Bukkit.getPlayer(player.minecraftUuid).kick(Component.text("An error occurred when our custom plugin tried to load your region data: this is our problem. Please contact an administrator using a ticket!"));
                throw new RuntimeException("Player " + player.id + " doesn't have a region but should have, as they seem to be linked!");
            }
            RunTask.sync(() -> setSpawnImmediate(player, playerRegion.region.spawn));
        });
    }

    private boolean hasPlayedBefore(ServerPlayer player) {
        ResultSet rs = Query.sqlSelect("select login_time from player_login where player_id = ? order by login_time asc limit 1", player.id);
        try {
            if (!rs.next()) return false;
            if (rs.getLong("login_time") < System.currentTimeMillis() - 1000) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
