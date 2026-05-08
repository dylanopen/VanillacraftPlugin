package com.lapisdev.vanillacraft.discord;

import net.dv8tion.jda.api.entities.MessageEmbed;

public class Embed {
    public static final String THUMBNAIL_URL = "https://i.ibb.co/YrpqTMM/vclogo.png";
    public static final String FOOTER = "Vanillacraft. No nonsense. Just Minecraft.";

    public static MessageEmbed info(String title, String description) {
        return new net.dv8tion.jda.api.EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setFooter(FOOTER)
                .setThumbnail(THUMBNAIL_URL)
                .setColor(0x7F33FF)
                .build();
    }

    public static MessageEmbed error(String title, String description) {
        return new net.dv8tion.jda.api.EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setFooter(FOOTER)
                .setThumbnail(THUMBNAIL_URL)
                .setColor(0xFF3333)
                .build();
    }

    public static MessageEmbed result(String title, String description) {
        return new net.dv8tion.jda.api.EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setFooter(FOOTER)
                .setThumbnail(THUMBNAIL_URL)
                .setColor(0x33FF33)
                .build();
    }
}
