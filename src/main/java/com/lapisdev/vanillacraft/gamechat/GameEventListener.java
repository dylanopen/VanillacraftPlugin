package com.lapisdev.vanillacraft.gamechat;

import com.lapisdev.vanillacraft.discord.Embed;
import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.task.RunTask;
import com.lapisdev.vanillacraft.vanish.VanishModule;
import net.dv8tion.jda.api.entities.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.lapisdev.vanillacraft.discord.Discord.jda;

public class GameEventListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        RunTask.async(_ -> {
            ServerPlayer player = ServerPlayer.fromMinecraftUuid(e.getPlayer().getUniqueId());

            if (VanishModule.vanishedPlayers.contains(player.id)) {
                e.joinMessage(null);
                return;
            };

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

            GameChatDiscord.send(ServerPlayer.fromMinecraftUuid(e.getPlayer().getUniqueId()), new Embed().resultColor()
                    .title(e.getPlayer().getName() + " rejoined the Minecraft server!")
                    .description("Players online: " + playersOnline)
                    .thumbnail(null)
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

            if (VanishModule.vanishedPlayers.contains(player.id)) {
                e.quitMessage(null);
                return;
            };

            GameChatDiscord.send(player, new Embed().warnColor()
                    .title(e.getPlayer().getName() + " left the Minecraft server!")
                    .description("Players online: " + playersOnline)
                    .thumbnail(null)
                    .build());
        });
    }

    @EventHandler
    public void onAdvancement(PlayerAdvancementDoneEvent e) {
        RunTask.async(_ -> {
            ServerPlayer player = ServerPlayer.fromMinecraftUuid(e.getPlayer().getUniqueId());
            if (VanishModule.vanishedPlayers.contains(player.id)) {
                e.message(Component.text("***")); // doesn't work
                return;
            };

            Advancement advancement = e.getAdvancement();
            if (advancement.getDisplay() == null) return;
            Component advancementNameComponent = advancement.getDisplay() != null ? advancement.getDisplay().title() : Component.text(advancement.getKey().getKey());
            String advancementName = PlainTextComponentSerializer.plainText().serialize(advancementNameComponent);
            Component advancementInfoComponent = advancement.getDisplay().description();
            String advancementInfo = PlainTextComponentSerializer.plainText().serialize(advancementInfoComponent);

            GameChatDiscord.send(player, new Embed().infoColor()
                    .title(e.getPlayer().getName() + " has made the advancement " + advancementName)
                    .description(advancementInfo)
                    .thumbnail(null)
                    .build());
        });
    }

    @EventHandler
    public void onDeath (PlayerDeathEvent e) {
        RunTask.async(_ -> {
            if (!e.getShowDeathMessages()) return;
            ServerPlayer player = ServerPlayer.fromMinecraftUuid(e.getEntity().getUniqueId());
            String deathMessage = e.getDeathMessage();

            if (VanishModule.vanishedPlayers.contains(player.id)) {
                e.deathMessage(Component.text("***")); // doesn't work
                return;
            };

            GameChatDiscord.send(player, new Embed().infoColor()
                    .title(deathMessage)
                    .thumbnail(null)
                    .build());
        });
    }

    public static void startup() {
        GameChatModule.discordChatChannel.sendMessageEmbeds(new Embed().resultColor()
                .title(":green_square: Vanillacraft has started up!")
                .build()).queue();
    }

    public static void shutdown() {
        GameChatModule.discordChatChannel.sendMessageEmbeds(new Embed().errorColor()
                .title(":red_square: Vanillacraft is stopping, it will be back in a few minutes!")
                .build()).queue();
    }
}
