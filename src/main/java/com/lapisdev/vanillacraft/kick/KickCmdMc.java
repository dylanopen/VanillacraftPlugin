package com.lapisdev.vanillacraft.kick;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class KickCmdMc {
    public static void register(Commands commands) {
        commands.register(Commands.literal("kick")
                        .then(Commands.argument("target", ArgumentTypes.players())
                                .then(Commands.argument("reason", ArgumentTypes.component())
                                        .executes(KickCmdMc::execute)))
                .build());
    }

    private static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        List<Player> players = ctx.getArgument("target", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource());
        Component reason = ctx.getArgument("reason", Component.class);
        for (Player player : players) {
            ServerPlayer dbPlayer = ServerPlayer.fromMinecraftUuid(player.getUniqueId());
            new Kick(dbPlayer, reason).execute();
        }
        return 1;
    }
}
