package com.lapisdev.vanillacraft.link;

import com.lapisdev.vanillacraft.task.RunTask;
import net.dv8tion.jda.api.components.label.Label;
import net.dv8tion.jda.api.components.textinput.TextInput;
import net.dv8tion.jda.api.components.textinput.TextInputStyle;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.modals.Modal;
import org.bukkit.Bukkit;

public class LinkQuestionSubmitListener extends ListenerAdapter {
    @Override
    public void onModalInteraction(ModalInteractionEvent e) {
        if (!e.getModalId().equals("linkquestion")) return;
        String minecraftUsername = e.getValue("username").getAsString();
        e.reply("Thanks! You have successfully linked your discord account to your Minecraft account and can join vanillacraft.org to start playing :)")
                .setEphemeral(true)
                .queue();
        RunTask.async((_) -> Linker.link(e.getUser().getId(), Bukkit.getOfflinePlayer(minecraftUsername).getUniqueId()));
        // todo: choose a region for the player
    }
}
