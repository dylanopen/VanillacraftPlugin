package com.lapisdev.vanillacraft.team;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class TeamDisbandCmd {
    public static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        Player mcplayer = (Player) ctx.getSource().getExecutor();
        ServerPlayer player = ServerPlayer.fromMinecraftUuid(mcplayer.getUniqueId());
        Team team = Team.fromTeamLeader(player);

        if (team == null){
            mcplayer.sendMessage(Component.text("You do not own a team", NamedTextColor.RED));
            return 0;
        }

        team.delete();

        mcplayer.sendMessage(Component.text("You have disbanded " + team.name, NamedTextColor.GREEN));
        return 1;
    }
}
