package com.lapisdev.vanillacraft.gamechat;

import com.lapisdev.vanillacraft.discord.Embed;
import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.vanish.VanishModule;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.ArrayList;

import static com.lapisdev.vanillacraft.discord.Discord.jda;

public class PlayerlistCmd extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if (!e.getName().equals("playerlist")) return;
        String title = Bukkit.getOnlinePlayers().size() + " players currently online";
        StringBuilder playerList = new StringBuilder();

        for (Player mcPlayer : Bukkit.getOnlinePlayers()) {
            ServerPlayer player = ServerPlayer.fromMinecraftUuid(mcPlayer.getUniqueId());
            if (VanishModule.vanishedPlayers.contains((player.id))) continue;
            User discordUser = jda.getUserById(player.discordUuid);
            playerList.append("- ").append(discordUser.getAsMention());
        }
        e.replyEmbeds(new Embed()
                .infoColor()
                .title(title)
                .description(playerList.toString())
                .build()).setEphemeral(true).queue();
    }
}
