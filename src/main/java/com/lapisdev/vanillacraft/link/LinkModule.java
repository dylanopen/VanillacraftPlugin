package com.lapisdev.vanillacraft.link;

import net.dv8tion.jda.api.JDA;

public class LinkModule {
    public LinkModule(JDA jda) {
        jda.addEventListener(new LinkCmdListener());
    }
}
