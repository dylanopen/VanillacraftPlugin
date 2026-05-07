package com.lapisdev.vanillacraft.link;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.components.buttons.Button;
import net.dv8tion.jda.api.components.label.Label;
import net.dv8tion.jda.api.components.textinput.TextInput;
import net.dv8tion.jda.api.components.textinput.TextInputStyle;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.modals.Modal;

import java.awt.Color;

public class StartLinkClickListener extends ListenerAdapter {
    @Override
    public void onButtonInteraction(ButtonInteractionEvent e) {
        if (!e.getButton().getCustomId().equals("startlink")) return;

        TextInput usernameInput = TextInput.create("username", TextInputStyle.SHORT)
                .setPlaceholder("Enter your Minecraft username here")
                .setMinLength(3)
                .setMaxLength(40)
                .build();

        Modal modal = Modal.create("linkquestion", "Link your account")
                .addComponents(Label.of("Minecraft username", usernameInput))
                .build();

        e.replyModal(modal).queue();
    }
}
