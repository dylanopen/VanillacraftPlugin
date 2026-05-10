package com.lapisdev.vanillacraft.maintenance;

import com.lapisdev.vanillacraft.discord.Embed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MaintenanceOnCmdDc extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if (!e.getName().equals("maintenance-on")) return;
        if (Maintenance.enabled) {
            e.replyEmbeds(new Embed().errorColor()
                    .title("Maintenance is already enabled!")
                    .description("The server is already under maintenance, you don't need to enable it again.")
                    .build()).setEphemeral(true).queue();
            return;
        }
        Maintenance.enable();
        e.replyEmbeds(new Embed().resultColor()
                .title("Maintenance enabled!")
                .description("The server is now under maintenance, all non-staff members will be kicked in 10 seconds.")
                .build()).setEphemeral(true).queue();
    }
}
