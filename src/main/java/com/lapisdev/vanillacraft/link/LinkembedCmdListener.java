package com.lapisdev.vanillacraft.link;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.components.buttons.Button;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

import java.awt.Color;

public class LinkembedCmdListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getAuthor().isBot()) return;
        if (!e.getMessage().getContentRaw().equals("-linkembed")) return;

        e.getMessage().delete().queue();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Link your Discord and Minecraft accounts together");
        embed.appendDescription("To get whitelisted to Vanillacraft, please enter your Minecraft username!");
        embed.setFooter("We will never ask for email addresses, passwords, 2FA codes, or other personal information: if you are asked for this, it is not us.");
        embed.setColor(Color.ORANGE);
        e.getChannel().sendMessageEmbeds(embed.build())
                .addComponents(ActionRow.of(
                        Button.primary("startlink", "Link Account!")
                )).queue();
    }
}
