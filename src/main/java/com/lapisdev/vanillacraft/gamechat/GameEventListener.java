package com.lapisdev.vanillacraft.gamechat;

import com.lapisdev.vanillacraft.discord.Embed;
import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.task.RunTask;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.lapisdev.vanillacraft.discord.Discord.jda;

public class GameEventListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        RunTask.async(_ -> {
            ServerPlayer player = ServerPlayer.fromMinecraftUuid(e.getPlayer().getUniqueId());
            if (!e.getPlayer().hasPlayedBefore()) {
                User discordUser = jda.retrieveUserById(player.discordUuid).complete();
                GameChatDiscord.send(player, new Embed().resultColor()
                        .title(e.getPlayer().getName() + " joined the Minecraft server for the first time!")
                        .description("Welcome to the server, " + discordUser.getAsMention() + "! We hope you enjoy your time here with us.")
                        .build());
                return;
            }

            String playersOnline = Bukkit.getOnlinePlayers().size() + ": ";
            for (Player mcPlayer : Bukkit.getOnlinePlayers()) {
                playersOnline += mcPlayer.getName() + ", ";
            }
            playersOnline = playersOnline.substring(0, playersOnline.length() - 2);

            GameChatDiscord.send(ServerPlayer.fromMinecraftUuid(e.getPlayer().getUniqueId()), new Embed().infoColor()
                    .title(e.getPlayer().getName() + " rejoined the Minecraft server!")
                    .description("Players online: " + playersOnline)
                    .build());
        });
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        RunTask.async(_ -> {
            ServerPlayer player = ServerPlayer.fromMinecraftUuid(e.getPlayer().getUniqueId());
            String playersOnline = Bukkit.getOnlinePlayers().size() - 1 + ": ";
            for (Player mcPlayer : Bukkit.getOnlinePlayers()) {
                if (mcPlayer.getUniqueId().equals(e.getPlayer().getUniqueId())) continue;
                playersOnline += mcPlayer.getName() + ", ";
            }

            GameChatDiscord.send(player, new Embed().warnColor()
                    .title(e.getPlayer().getName() + " left the Minecraft server!")
                    .description("Players online: " + playersOnline)
                    .build());
        });
    }
}
