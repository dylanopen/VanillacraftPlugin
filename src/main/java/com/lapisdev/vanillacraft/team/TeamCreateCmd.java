package com.lapisdev.vanillacraft.team;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;

public class TeamCreateCmd {
    public static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        Player mcplayer = (Player) ctx.getSource().getExecutor();
        String name = ctx.getArgument("name", String.class);
        String suffix = ctx.getArgument("suffix", String.class);
        ServerPlayer player = ServerPlayer.fromMinecraftUuid(mcplayer.getUniqueId());

        if (suffix.length() > 6) {
            mcplayer.sendMessage("The team suffix must be at most 5 letters long");
            return 0;
        }

        if (Team.fromTeamName(name) != null) {
            mcplayer.sendMessage("There is already a team with that name");
            return 0;
        }

        if (Team.fromTeamSuffix(suffix) != null) {
            mcplayer.sendMessage("There is already a team with that name");
            return 0;
        }

        if (Team.fromTeamLeader(player) != null) {
            mcplayer.sendMessage("You already have made a team");
            return 0;
        }

        String luckpermSuffix = "&7[" + suffix.toUpperCase() + "]";

        Team team = new Team();
        team.name = name;
        team.suffix = suffix.toUpperCase();
        team.leader = player;
        team.save();
        team = Team.fromTeamName(name);

        new PlayerTeam(player, team).save();
        mcplayer.sendMessage("You have created team " + name + " with the suffix [" + suffix.toUpperCase() + "]");

        return 1;
    }
}
