package com.lapisdev.vanillacraft.discord;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import static com.lapisdev.vanillacraft.discord.Discord.jda;

public class DiscordModule {
    public DiscordModule() {
        jda = JDABuilder.createDefault(System.getenv("VANILLA_DISCORD_BOT_TOKEN"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();
        new DiscordCommands();

        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
