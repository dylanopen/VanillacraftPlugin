package com.lapisdev.vanillacraft.discord;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import static com.lapisdev.vanillacraft.discord.Discord.jda;

public class DiscordCommands {
    public DiscordCommands() {
        jda.updateCommands().addCommands(

                staffCmd(Commands.slash("ban", "Ban a user from the Minecraft server, and prevent them from viewing discord channels.")
                        .addOptions(new OptionData(OptionType.USER, "user", "Ban a player's account associated with this discord account."))
                        .addOptions(new OptionData(OptionType.STRING, "reason", "The reason to provide for the ban."))
                        .addOptions(new OptionData(OptionType.STRING, "duration", "The duration of the ban (e.g. `1d`, `2h30m`, `45m`, etc.), else permanent."))),

                staffCmd(Commands.slash("mckick", "Kick a player from the Minecraft server, given their Minecraft username or a Discord ping.")
                        .addOptions(new OptionData(OptionType.USER, "dcuser", "Kick a player's Minecraft account associated with this discord account."))
                        .addOptions(new OptionData(OptionType.STRING, "mcuser", "Kick the Minecraft account with the given username."))
                        .addOptions(new OptionData(OptionType.STRING, "reason", "The reason to provide for the kick."))),

                staffCmd(Commands.slash("mcrestart", "Restart the Minecraft server, only to be used if something is wrong and it needs a restart.")),

                staffCmd(Commands.slash("maintenance-on", "Put the Minecraft server under maintenance, kicking all non-staff members after 10 seconds.")),

                staffCmd(Commands.slash("maintenance-off", "Take the Minecraft server out of maintenance mode, allowing all members to join again.")),

                staffCmd(Commands.slash("staffrole-add", "Add a staff role to a player")
                        .addOptions(new OptionData(OptionType.USER, "user", "The user to add the staff role to"))
                        .addOptions(new OptionData(OptionType.STRING, "role", "The staff role to add, e.g. `staff`, `admin`, etc."))),

                staffCmd(Commands.slash("staffrole-remove", "Remove a staff role from a player")
                        .addOptions(new OptionData(OptionType.USER, "user", "The user to remove the staff role from"))
                        .addOptions(new OptionData(OptionType.STRING, "role", "The staff role to remove, e.g. `staff`, `admin`, etc."))),

                staffCmd(Commands.slash("playerlist", "List all members currently playing on Vanillacraft.")),

                staffCmd(Commands.slash("mcmd", "Run a Minecraft command from Discord")
                        .addOptions(new OptionData(OptionType.STRING, "command", "The command to run", true)))
        ).queue();
    }

    private SlashCommandData staffCmd(SlashCommandData cmd) {
        return cmd.setContexts(InteractionContextType.GUILD)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MESSAGE_MANAGE));
    }
}
