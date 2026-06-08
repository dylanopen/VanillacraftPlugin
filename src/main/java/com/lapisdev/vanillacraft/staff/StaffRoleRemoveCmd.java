package com.lapisdev.vanillacraft.staff;

import org.bukkit.Bukkit;

import com.lapisdev.vanillacraft.database.Query;
import com.lapisdev.vanillacraft.discord.Embed;
import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.task.RunTask;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class StaffRoleRemoveCmd extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
	if (!e.getName().equals("staffrole-remove")) return;

	if (!e.getMember().hasPermission(net.dv8tion.jda.api.Permission.ADMINISTRATOR)) {
	    e.reply("You don't have permission to remove a player's staff").setEphemeral(true).queue();
	    return;
	}

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

	if (PlayerStaffRole.fromPlayer(player).stream().noneMatch(psr -> psr.staffRole.id == staffRole.id)) {
	    e.reply(targetPlayer.getAsMention() + " doesn't have the " + staffRole.name + " `staff` role").setEphemeral(true).queue();
	    return;
	}

	Query.sqlDelete("delete from player_staff_role where player_id = ? and staff_role_id = ?", player.id, staffRole.id);

	String playerName = Bukkit.getOfflinePlayer(player.minecraftUuid).getName();

	// remove luckperms role in-game
	RunTask.sync(() -> {
	    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + playerName + " parent remove " + staffRole.luckpermsGroup);
	});

	// remove discord role
	e.getGuild().removeRoleFromMember(targetPlayer, e.getGuild().getRoleById(staffRole.discordRoleId)).queue();

	String title = "Removed '" + staffRole.name + "' from " + targetPlayer.getName();
	String description = targetPlayer.getAsMention() + " no longer has the " + staffRole.name + " `staff` role and cannot use its permissions in-game and in discord.";
	e.replyEmbeds(new Embed()
		.resultColor()
		.title(title)
		.description(description)
		.build())
	    .setEphemeral(false).queue();
    }
}
