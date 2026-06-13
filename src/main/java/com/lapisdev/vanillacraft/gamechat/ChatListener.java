package com.lapisdev.vanillacraft.gamechat;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.task.RunTask;
import com.lapisdev.vanillacraft.vanish.VanishModule;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncChatEvent e) {
        RunTask.async(_ ->{
            ServerPlayer player = ServerPlayer.fromMinecraftUuid(e.getPlayer().getUniqueId());
            if (VanishModule.vanishedPlayers.contains(player.id)) return;

            ServerPlayer mcPlayer = ServerPlayer.fromMinecraftUuid(e.getPlayer().getUniqueId());
            PlainTextComponentSerializer plainSerializer = PlainTextComponentSerializer.plainText();
            GameChatDiscord.send(mcPlayer, plainSerializer.serialize(e.message()));
        });
    }
}
