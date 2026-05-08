package com.lapisdev.vanillacraft.gamechat;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import org.bukkit.Bukkit;

public class GameChatDiscord {
    public static void send(ServerPlayer player, String message) {
        String username = Bukkit.getPlayer(player.minecraftUuid).getName();
        GameChatModule.discordChatChannel.sendMessage("**" + username + ":** " + message).queue();
    }
}
