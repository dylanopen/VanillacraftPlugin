package com.lapisdev.vanillacraft.kick;

import static com.lapisdev.vanillacraft.discord.Discord.jda;

public class KickModule {
    public KickModule() {
        KickCommandRegistry.register();
        jda.addEventListener(new MckickCmdDc());
    }
}
