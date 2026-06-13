package com.lapisdev.vanillacraft.discord;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.staff.PlayerStaffRole;
import com.lapisdev.vanillacraft.task.RunTask;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class McmdCmd extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        if (!e.getName().equals("mcmd")) return;

        String command = e.getOption("command").getAsString();
        if (command.startsWith("/")) command = command.substring(1);
        String firstWord = command.split(" ")[0];

        ServerPlayer player = ServerPlayer.fromDiscordUuid(e.getUser().getId());
        if (player == null) {
            e.reply("Your account is not linked!").setEphemeral(true).queue();
            return;
        }

        boolean hasPermission = false;
        
        ArrayList<PlayerStaffRole> staffRoles = PlayerStaffRole.fromPlayer(player);
        for (PlayerStaffRole psr : staffRoles) {
            if (psr.staffRole.name.equalsIgnoreCase("administrator")) {
                hasPermission = true;
                break;
            }
        }

        if (!hasPermission) {
            e.reply("You do not have permission to run this command!").setEphemeral(true).queue();
            return;
        }

        String finalCommand = command;
        RunTask.sync(() -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), finalCommand);
        });

        e.replyEmbeds(new Embed().resultColor()
                .title("Command Executed")
                .description("Executed: `/" + command + "`")
                .build()).queue();
    }
}
