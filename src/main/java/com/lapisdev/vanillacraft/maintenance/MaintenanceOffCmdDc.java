package com.lapisdev.vanillacraft.maintenance;

import com.lapisdev.vanillacraft.discord.Embed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MaintenanceOffCmdDc extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if (!e.getName().equals("maintenance-off")) return;
        if (Maintenance.enabled) {
            e.replyEmbeds(new Embed().errorColor()
                    .title("Maintenance is not enabled!")
                    .description("The server is not currently under maintenance, you don't need to disable it.")
                    .build()).setEphemeral(true).queue();
            return;
        }
        Maintenance.enable();
        e.replyEmbeds(new Embed().resultColor()
                .title("Maintenance disabled!")
                .description("The server is no longer under maintenance, all linked players are able to join.")
                .build()).setEphemeral(true).queue();
    }
}
