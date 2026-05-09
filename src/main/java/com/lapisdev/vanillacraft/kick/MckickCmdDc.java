package com.lapisdev.vanillacraft.kick;

import com.lapisdev.vanillacraft.discord.Embed;
import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.task.RunTask;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.awt.Color;

import static com.lapisdev.vanillacraft.discord.Discord.jda;

public class MckickCmdDc extends ListenerAdapter {
    public static void register() {
        jda.updateCommands().addCommands(Commands.slash("mckick", "Kick a player from the Minecraft server, given their Minecraft username or a Discord ping.")
                        .addOptions(new OptionData(OptionType.USER, "dcuser", "Kick a player's Minecraft account associated with this discord account."))
                        .addOptions(new OptionData(OptionType.STRING, "mcuser", "Kick the Minecraft account with the given username."))
                        .addOptions(new OptionData(OptionType.STRING, "reason", "The reason to provide for the kick."))
                        .setContexts(InteractionContextType.GUILD)
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MESSAGE_MANAGE)))
                .queue();
        jda.addEventListener(new MckickCmdDc());
    }

    @Override
    public void onSlashCommandInteraction(@NonNull SlashCommandInteractionEvent e) {
        if (!e.getName().equals("mckick")) return;
        OptionMapping dcUser = e.getOption("dcuser");
        OptionMapping mcUsername = e.getOption("mcuser");
        OptionMapping reason = e.getOption("reason");
        ServerPlayer player = null;

        if (dcUser != null && mcUsername != null) {
            e.replyEmbeds(new Embed().errorColor()
                    .title("Only one user option required")
                    .description("You only need to specify **either** a discord user **or** a Minecraft username when kicking a user.")
                    .build()).setEphemeral(true).queue();
            return;
        }
        if (dcUser != null) {
            player = ServerPlayer.fromDiscordUuid(dcUser.getAsUser().getId());
        }
        if (mcUsername != null) {
            player = ServerPlayer.fromMinecraftUuid(Bukkit.getOfflinePlayer(mcUsername.getAsString()).getUniqueId());
        }
        if (player == null) {
            e.replyEmbeds(new Embed().errorColor()
                            .title("That player doesn't exist")
                    .description("...Well, at least, they haven't joined the server before. Are you sure you entered the username or discord ping correctly?")
                    .build()).setEphemeral(true).queue();
            return;
        }
        TextComponent reasonComponent = Component.text("No reason provided.", NamedTextColor.AQUA);
        if (reason != null) {
            reasonComponent = Component.text(reason.getAsString(), NamedTextColor.AQUA);
        }

        Player playerMc = Bukkit.getPlayer(player.minecraftUuid);
        if (playerMc == null) {
            e.replyEmbeds(new Embed().errorColor()
                    .title("That player isn't online")
                    .description("...We've definitely seen them around before, but they don't seem to be here right now.")
                    .build()).setEphemeral(true).queue();
            return;
        }
        User playerDc = jda.getUserById(player.discordUuid);

        e.replyEmbeds(new Embed().resultColor()
                        .title("Kicked " + playerMc.getName() + " from the game.")
                .description("Reason: " + reasonComponent.content())
                .build()).queue();

        Kick kick = new Kick(player, reasonComponent);
        RunTask.sync(kick::execute);
    }
}
