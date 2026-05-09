package com.lapisdev.vanillacraft.discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;

public class Embed {
    public String title = null;
    public String description = null;
    public Color color = null;
    public String thumbnailUrl = "https://i.ibb.co/YrpqTMM/vclogo.png";
    public String footer = "Vanillacraft. No nonsense. Just Minecraft.";

    public Embed() {}

    public Embed title(String title) {
        this.title = title;
        return this;
    }

    public Embed description(String description) {
        this.description = description;
        return this;
    }

    public Embed color(Color color) {
        this.color = color;
        return this;
    }

    public Embed thumbnail(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }

    public Embed footer(String footer) {
        this.footer = footer;
        return this;
    }

    public Embed infoColor() {
        this.color = new Color(0x7F33FF);
        return this;
    }

    public Embed warnColor() {
        this.color = new Color(0xCCAA33);
        return this;
    }

    public Embed errorColor() {
        this.color = new Color(0xFF3333);
        return this;
    }

    public Embed resultColor() {
        this.color = new Color(0x33FF33);
        return this;
    }

    public MessageEmbed build() {
        EmbedBuilder builder = new EmbedBuilder();
        if (title != null) builder.setTitle(title);
        if (description != null) builder.setDescription(description);
        if (color != null) builder.setColor(color);
        if (thumbnailUrl != null) builder.setThumbnail(thumbnailUrl);
        if (footer != null) builder.setFooter(footer);
        return builder.build();
    }

}
