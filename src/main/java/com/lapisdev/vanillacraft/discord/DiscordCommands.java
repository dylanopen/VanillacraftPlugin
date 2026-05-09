package com.lapisdev.vanillacraft.discord;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import static com.lapisdev.vanillacraft.discord.Discord.jda;

public class DiscordCommands {
    public DiscordCommands() {
        jda.updateCommands().addCommands(

                Commands.slash("ban", "Ban a user from the Minecraft server, and prevent them from viewing discord channels.")
                        .addOptions(new OptionData(OptionType.USER, "user", "Ban a player's account associated with this discord account."))
                        .addOptions(new OptionData(OptionType.STRING, "reason", "The reason to provide for the ban."))
                        .addOptions(new OptionData(OptionType.STRING, "duration", "The duration of the ban (e.g. `1d`, `2h30m`, `45m`, etc.), else permanent."))
                        .setContexts(InteractionContextType.GUILD)
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MESSAGE_MANAGE)),

                Commands.slash("mckick", "Kick a player from the Minecraft server, given their Minecraft username or a Discord ping.")
                        .addOptions(new OptionData(OptionType.USER, "dcuser", "Kick a player's Minecraft account associated with this discord account."))
                        .addOptions(new OptionData(OptionType.STRING, "mcuser", "Kick the Minecraft account with the given username."))
                        .addOptions(new OptionData(OptionType.STRING, "reason", "The reason to provide for the kick."))
                        .setContexts(InteractionContextType.GUILD)
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MESSAGE_MANAGE)),

                Commands.slash("mcrestart", "Restart the Minecraft server, only to be used if something is wrong and it needs a restart.")
                        .setContexts(InteractionContextType.GUILD)
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MESSAGE_MANAGE)),

                Commands.slash("playerlist", "Get a list of all players currently online on the Minecraft server.")
                        .setContexts(InteractionContextType.GUILD)
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.VIEW_CHANNEL))

        ).queue();
    }
}
