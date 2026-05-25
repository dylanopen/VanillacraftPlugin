package com.lapisdev.vanillacraft.staff;

import org.bukkit.Bukkit;

import com.lapisdev.vanillacraft.discord.Embed;
import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.task.RunTask;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class StaffRoleAddCmd extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
	if (!e.getName().equals("staffrole-add")) return;

	String staffRoleName = e.getOption("role").getAsString();
	StaffRole staffRole = StaffRole.fromName(staffRoleName);
	if (staffRole == null) {
	    e.reply("No staff role with that name found").setEphemeral(true).queue();
	    return;
	}

	User targetPlayer = e.getOption("user").getAsUser();
	ServerPlayer player = ServerPlayer.fromDiscordUuid(targetPlayer.getId());
	if (player == null) {
	    e.reply("No player with that name found").setEphemeral(true).queue();
	    return;
	}
	
	PlayerStaffRole playerStaffRole = new PlayerStaffRole(player, staffRole);
	playerStaffRole.save();

	// add to luckperms in-game
	RunTask.sync(() -> {
	    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.minecraftUuid + " parent add " + staffRole.luckpermsGroup);
	});

	// add discord role
	e.getGuild().addRoleToMember(targetPlayer, e.getGuild().getRoleById(staffRole.discordRoleId)).queue();

	String title = "Added staff role " + staffRole.name + " to " + targetPlayer.getAsMention();
	String description = targetPlayer.getAsMention() + " now has the " + staffRole.name + " staff role and can use its permissions in-game and in discord.";
	e.replyEmbeds(new Embed()
		.infoColor()
		.title(title)
		.description(description)
		.build())
	    .setEphemeral(false).queue();
    }
}
