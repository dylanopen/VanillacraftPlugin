package com.lapisdev.vanillacraft.team;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;

import static com.lapisdev.vanillacraft.database.Query.sqlDelete;

public class TeamAcceptCmd {
    public static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        Player mcplayer = (Player) ctx.getSource().getExecutor();
        ServerPlayer player = ServerPlayer.fromMinecraftUuid(mcplayer.getUniqueId());
        String name = ctx.getArgument("name", String.class);
        Team team = Team.fromTeamName(name);

        if (team == null) {
            mcplayer.sendMessage("That team does not exist.");
            return 0;
        }

        if(!TeamInvite.playerHasInvite(player, team)) {
            mcplayer.sendMessage("You do not have an invite from that team");
            return 0;
        }

        new PlayerTeam(player, team).save();
        sqlDelete("delete from team_invite where player_id = ? and team_id = ?", player.id, team.id);
        mcplayer.sendMessage("You have joined " + team.name);

        return 1;
    }
}
