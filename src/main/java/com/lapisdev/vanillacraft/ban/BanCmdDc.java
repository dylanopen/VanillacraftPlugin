package com.lapisdev.vanillacraft.ban;

import com.lapisdev.vanillacraft.discord.Embed;
import com.lapisdev.vanillacraft.duration.DurationDisplay;
import com.lapisdev.vanillacraft.player.ServerPlayer;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jspecify.annotations.NonNull;

import static com.lapisdev.vanillacraft.discord.Discord.jda;

public class BanCmdDc extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NonNull SlashCommandInteractionEvent e) {
        if (!e.getName().equals("ban")) return;
        OptionMapping dcUser = e.getOption("user");
        OptionMapping duration = e.getOption("duration");
        OptionMapping reason = e.getOption("reason");
        ServerPlayer player = null;

        if (dcUser == null) {
            e.replyEmbeds(new Embed().errorColor()
                    .title("You must specify a player to ban")
                    .description("Please specify a player to ban by pinging them in the `user` argument.")
                    .build()).setEphemeral(true).queue();
            return;
        }
        player = ServerPlayer.fromDiscordUuid(dcUser.getAsUser().getId());

        if (player == null) {
            e.replyEmbeds(new Embed().errorColor()
                    .title("That player doesn't exist")
                    .description("...Well, at least, they linked their account. Are you sure you entered the username or discord ping correctly?")
                    .build()).setEphemeral(true).queue();
            return;
        }
        TextComponent reasonComponent = Component.text("No reason provided.", NamedTextColor.AQUA);
        if (reason != null) {
            reasonComponent = Component.text(reason.getAsString(), NamedTextColor.AQUA);
        }

        long endTime = Long.MAX_VALUE;
        if (duration != null) {
            try {
                endTime = System.currentTimeMillis() + DurationDisplay.fromString(duration.getAsString());
            } catch (Exception ex) {
                e.replyEmbeds(new Embed().errorColor()
                        .title("Invalid duration format")
                        .description("The duration format you entered is invalid. Please enter a valid duration (e.g. `1d`, `2h30m`, `45m`, etc.)")
                        .build()).setEphemeral(true).queue();
                return;
            }
        }

        OfflinePlayer playerMc = Bukkit.getOfflinePlayer(player.minecraftUuid);
        User playerDc = jda.getUserById(player.discordUuid);

        e.replyEmbeds(new Embed().resultColor()
                .title("Banned " + playerMc.getName())
                .description("Reason: " + reasonComponent.content()
                        + "\nExpires: " + (endTime == Long.MAX_VALUE ? "Never" : "<t:" + endTime/1000 + ":R>")
                        + "\n" + playerDc.getAsMention()
                )
                .footer()
                .build()).queue();

        new Ban(player, reasonComponent.content(), endTime).execute();
    }
}
