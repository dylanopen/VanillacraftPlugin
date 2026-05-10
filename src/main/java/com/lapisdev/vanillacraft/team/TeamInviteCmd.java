package com.lapisdev.vanillacraft.team;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import org.bukkit.entity.Player;

public class TeamInviteCmd {
    public static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        Player mcplayer = (Player) ctx.getSource().getExecutor();
        ServerPlayer player = ServerPlayer.fromMinecraftUuid(mcplayer.getUniqueId());

        PlayerSelectorArgumentResolver targetResolver = ctx.getArgument("player", PlayerSelectorArgumentResolver.class);
        Player targetMCPlayer = targetResolver.resolve(ctx.getSource()).getFirst();
        ServerPlayer targetPlayer = ServerPlayer.fromMinecraftUuid(targetMCPlayer.getUniqueId());

        Team team = Team.fromTeamLeader(player);

        if (team == null){
            mcplayer.sendMessage("You do not own a team");
            return 0;
        }

        TeamInvite invite = new TeamInvite();
        invite.player = targetPlayer;
        invite.team = team;
        invite.save();

        mcplayer.sendMessage("You have invited " + targetMCPlayer.getName() + " to " + team.name);
        return 1;
    }
}
