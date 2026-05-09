package com.lapisdev.vanillacraft.ban;

import com.lapisdev.vanillacraft.duration.DurationDisplay;
import com.lapisdev.vanillacraft.kick.Kick;
import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.task.RunTask;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.Duration;
import java.util.ArrayList;

public class BanJoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        RunTask.async(_ -> {;
            ServerPlayer player = ServerPlayer.fromMinecraftUuid(e.getPlayer().getUniqueId());
            if (player == null) return; // will be kicked by WhitelistModule
            ArrayList<Ban> activeBans = Ban.activeFromPlayer(player);
            if (activeBans.size() <= 0) return;

            Ban lastExpiringBan = activeBans.getLast();
            String remainingTime = DurationDisplay.formatDuration(lastExpiringBan.endTime - System.currentTimeMillis());
            if (lastExpiringBan.endTime == Long.MAX_VALUE) remainingTime = "Permanent";
            String kickMessage = "You have been banned from Vanillacraft.\nReason: " + lastExpiringBan.reason + "\nRemaining time: " + remainingTime;
            RunTask.sync(() -> new Kick(player, Component.text(kickMessage, NamedTextColor.AQUA)).execute());
        });
    }
}
