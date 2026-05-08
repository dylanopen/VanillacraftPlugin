package com.lapisdev.vanillacraft.gamechat;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import static com.lapisdev.vanillacraft.VanillacraftPlugin.handle;
import static com.lapisdev.vanillacraft.discord.Discord.jda;

public class GameChatModule {
    public static TextChannel discordChatChannel;

    public GameChatModule() {
        discordChatChannel = jda.getTextChannelById(1502443892614762576L);
        handle(new ChatListener());
        handle(new GameEventListener());
    }
}
