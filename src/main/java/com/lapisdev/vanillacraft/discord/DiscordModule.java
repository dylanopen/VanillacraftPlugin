package com.lapisdev.vanillacraft.discord;

import com.lapisdev.vanillacraft.link.LinkModule;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class DiscordModule {
    public DiscordModule() {
        JDA jda = JDABuilder.createDefault(System.getenv("VANILLA_DISCORD_BOT_TOKEN"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();

        new LinkModule(jda);

        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
