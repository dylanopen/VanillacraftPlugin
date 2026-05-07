package com.lapisdev.vanillacraft.link;

import net.dv8tion.jda.api.JDA;

public class LinkModule {
    public LinkModule(JDA jda) {
        jda.addEventListener(new LinkCmdListener());
        jda.addEventListener(new LinkembedCmdListener());
        jda.addEventListener(new StartLinkClickListener());
        jda.addEventListener(new LinkQuestionSubmitListener());
    }
}
