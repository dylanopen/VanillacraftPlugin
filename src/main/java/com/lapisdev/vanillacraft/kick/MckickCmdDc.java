package com.lapisdev.vanillacraft.kick;

import com.lapisdev.vanillacraft.player.ServerPlayer;
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
            e.reply("You only need to specify **either** a discord user **or** a minecraft username.")
                    .setEphemeral(true).queue();
            return;
        }
        if (dcUser != null) {
            player = ServerPlayer.fromDiscordUuid(dcUser.getAsUser().getId());
        }
        if (mcUsername != null) {
            player = ServerPlayer.fromMinecraftUuid(Bukkit.getOfflinePlayer(mcUsername.getAsString()).getUniqueId());
        }
        if (player == null) {
            e.reply("That player does not exist.")
                    .setEphemeral(true).queue();
            return;
        }
        TextComponent reasonComponent = Component.text("No reason provided.", NamedTextColor.AQUA);
        if (reason != null) {
            reasonComponent = Component.text(reason.getAsString(), NamedTextColor.AQUA);
        }

        Player playerMc = Bukkit.getPlayer(player.minecraftUuid);
        if (playerMc == null) {
            MessageEmbed embed = new EmbedBuilder()
                    .setColor(Color.RED)
                    .appendDescription("That player is not online.")
                    .build();
            e.replyEmbeds(embed).setEphemeral(true).queue();
            return;
        }
        User playerDc = jda.getUserById(player.discordUuid);

        MessageEmbed embed = new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setAuthor(e.getUser().getEffectiveName())
                .appendDescription("Disconnected " + playerMc.getName() + " (" + playerDc.getAsMention() + ") from the Minecraft server.\n")
                .appendDescription("Reason: " + reasonComponent.content())
                .build();
        e.replyEmbeds(embed).queue();

        new Kick(player, reasonComponent).execute();
    }
}
