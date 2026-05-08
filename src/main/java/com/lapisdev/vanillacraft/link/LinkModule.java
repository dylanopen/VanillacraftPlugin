package com.lapisdev.vanillacraft.link;

import net.dv8tion.jda.api.JDA;

import static com.lapisdev.vanillacraft.discord.Discord.jda;

public class LinkModule {
    public LinkModule() {
        jda.addEventListener(new LinkembedCmdListener());
        jda.addEventListener(new StartLinkClickListener());
        jda.addEventListener(new LinkQuestionSubmitListener());
    }
}
