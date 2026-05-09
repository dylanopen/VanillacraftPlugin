package com.lapisdev.vanillacraft.gamechat;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.task.RunTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        RunTask.async(_ ->{
            ServerPlayer player = ServerPlayer.fromMinecraftUuid(e.getPlayer().getUniqueId());
            GameChatDiscord.send(player, e.getMessage());
        });
    }
}
