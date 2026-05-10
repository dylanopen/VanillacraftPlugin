package com.lapisdev.vanillacraft.maintenance;

import com.lapisdev.vanillacraft.kick.Kick;
import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.staff.PlayerStaffRole;
import com.lapisdev.vanillacraft.task.RunTask;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

public class MaintenanceJoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!Maintenance.enabled) return;
        RunTask.async(_ -> {
            ServerPlayer player = ServerPlayer.fromMinecraftUuid(e.getPlayer().getUniqueId());
            ArrayList<PlayerStaffRole> staffRoles = PlayerStaffRole.fromPlayer(player);
            if (!staffRoles.isEmpty()) return;
            RunTask.sync(() -> new Kick(player, Component.text("The server is under maintenance! Please rejoin shorty once the staff team have finished.")).execute());
        });
    }
}
