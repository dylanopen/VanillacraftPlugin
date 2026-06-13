package com.lapisdev.vanillacraft.maintenance;

import com.lapisdev.vanillacraft.kick.Kick;
import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.staff.PlayerStaffRole;
import com.lapisdev.vanillacraft.task.RunTask;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Maintenance {
    public static boolean enabled;

    public static void enable() {
        enabled = true;
        Bukkit.getServer().sendMessage(Component.text("The server is under maintenance! You will be kicked in 10 seconds.", NamedTextColor.RED));
        RunTask.async(_ -> Maintenance.kickMembers(), 20*10);
    }

    public static void disable() {
        enabled = false;
    }

    public static void kickMembers() {
        for (Player mcPlayer : Bukkit.getOnlinePlayers()) {
            ServerPlayer player = ServerPlayer.fromMinecraftUuid(mcPlayer.getUniqueId());
            ArrayList<PlayerStaffRole> playerStaffRoles = PlayerStaffRole.fromPlayer(player);
            if (playerStaffRoles.isEmpty()) {
                RunTask.sync(() -> new Kick(player, Component.text("The server is under maintenance! Please rejoin shorty once the staff team have finished.", NamedTextColor.RED)).execute());
            }
        }
    }
}
