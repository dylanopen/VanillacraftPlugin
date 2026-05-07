package com.lapisdev.vanillacraft.link;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

public class LinkCmdListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getAuthor().isBot()) return;
        if (!e.getMessage().getContentRaw().startsWith("-link ")) return;

        String[] args = e.getMessage().getContentRaw().split(" ");
        if (args.length != 2) {
            e.getMessage().reply("Usage: -link <username>").queue();
            return;
        }

        String username = args[1];

        Linker.link(e.getAuthor().getId(), Bukkit.getOfflinePlayer(username).getUniqueId());
    }
}
