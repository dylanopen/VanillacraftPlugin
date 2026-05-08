package com.lapisdev.vanillacraft.gamechat;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Webhook;
import org.bukkit.Bukkit;

import static com.lapisdev.vanillacraft.discord.Discord.jda;

public class GameChatDiscord {
    private static int lastWebhookUser = 0;
    private static Webhook lastWebhook;

    public static void send(ServerPlayer player, String message) {
        String username = Bukkit.getPlayer(player.minecraftUuid).getName();
        User discordUser = jda.retrieveUserById(player.discordUuid).complete();

        if (lastWebhookUser == player.id && lastWebhook != null) {
            sendToWebhook(lastWebhook, message);
            return;
        }

        long startTime = System.currentTimeMillis();

        discordUser.getAvatar().downloadAsIcon().thenAccept(icon -> {
            GameChatModule.discordChatChannel.createWebhook("game_chat_player_" + player.id)
                    .setName(discordUser.getEffectiveName())
                    .setAvatar(icon)
                    .queue(webhook -> {
                        if (lastWebhook != null) lastWebhook.delete().queue();
                        lastWebhookUser = player.id;
                        lastWebhook = webhook;
                        sendToWebhook(webhook, message);
                    });
        });
    }

    private static void sendToWebhook(Webhook webhook, String message) {
        webhook.sendMessage(message).queue();
    }
}
