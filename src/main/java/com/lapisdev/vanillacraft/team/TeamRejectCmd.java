package com.lapisdev.vanillacraft.team;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import static com.lapisdev.vanillacraft.database.Query.sqlDelete;

public class TeamRejectCmd {
    public static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        Player mcplayer = (Player) ctx.getSource().getExecutor();
        ServerPlayer player = ServerPlayer.fromMinecraftUuid(mcplayer.getUniqueId());
        String name = ctx.getArgument("name", String.class);
        Team team = Team.fromTeamName(name);

        if (team == null) {
            mcplayer.sendMessage(Component.text("That team does not exist.", NamedTextColor.RED));
            return 0;
        }

        if(!TeamInvite.playerHasInvite(player, team)) {
            mcplayer.sendMessage(Component.text("You do not have an invite from that team", NamedTextColor.RED));
            return 0;
        }

        sqlDelete("delete from team_invite where player_id = ? and team_id = ?", player.id, team.id);
        mcplayer.sendMessage(Component.text("You have rejected an invite from " + team.name, NamedTextColor.GREEN));

        return 1;
    }
}
