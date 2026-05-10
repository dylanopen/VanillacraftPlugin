package com.lapisdev.vanillacraft.team;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;

import static com.lapisdev.vanillacraft.database.Query.sqlDelete;

public class TeamLeaveCmd {
    public static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        Player mcplayer = (Player) ctx.getSource().getExecutor();
        ServerPlayer player = ServerPlayer.fromMinecraftUuid(mcplayer.getUniqueId());
        Team team = Team.fromTeamMember(player);

        if (team == null){
            mcplayer.sendMessage("You are not in a team");
            return 0;
        }

        sqlDelete("delete from player_team where player_id = ?", player);

        mcplayer.sendMessage("You have left " + team.name);

        return 1;
    }
}
