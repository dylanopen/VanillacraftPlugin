package com.lapisdev.vanillacraft.gamechat;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class DiscordChatListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getAuthor().isBot()) return;
        if (e.isWebhookMessage()) return;
        if (e.getChannel() != GameChatModule.discordChatChannel) return; // may need to check IDs instead

        ServerPlayer player = ServerPlayer.fromDiscordUuid(e.getAuthor().getId());
        if (player == null) return;
        OfflinePlayer mcPlayer = Bukkit.getOfflinePlayer(player.minecraftUuid);
        Bukkit.getServer().sendMessage(Component.text("[" + mcPlayer.getName() + "] ", NamedTextColor.GRAY)
                .append(Component.text(e.getMessage().getContentDisplay(), NamedTextColor.WHITE)));
    }
}
