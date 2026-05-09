package com.lapisdev.vanillacraft.link;

import com.lapisdev.vanillacraft.discord.Embed;
import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.components.buttons.Button;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LinkembedCmdListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getAuthor().isBot()) return;
        if (!e.getMessage().getContentRaw().equals("-linkembed")) return;

        e.getMessage().delete().queue();

        e.getChannel().sendMessageEmbeds(new Embed().infoColor()
                .title("Link your Discord and Minecraft accounts together")
                .description("To get whitelisted to Vanillacraft, please click the **Link Account!** button above and enter your Minecraft username!\n\n" +
                        "*We will never ask for email addresses, passwords, 2FA codes, or other personal information.*")
                .build()).addComponents(ActionRow.of(
                Button.success("startlink", "Link Account!").withEmoji(Emoji.fromUnicode("🔗"))
        )).queue();
    }
}
