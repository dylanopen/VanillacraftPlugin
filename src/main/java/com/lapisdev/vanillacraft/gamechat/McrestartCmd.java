package com.lapisdev.vanillacraft.gamechat;

import com.lapisdev.vanillacraft.discord.Embed;
import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.task.RunTask;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static com.lapisdev.vanillacraft.discord.Discord.jda;

public class McrestartCmd extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if (!e.getName().equals("mcrestart")) return;
        e.replyEmbeds(new Embed().resultColor()
                .title("Initiated a server restart")
                .description("Server will restart in 30 seconds")
                .build()).queue();
        Bukkit.getServer().sendMessage(Component.text("The server has been force-restarted by an administrator. Please be ready for a restart in 30 seconds.", NamedTextColor.RED));
        RunTask.sync(Bukkit::shutdown, 30*20);
    }
}
