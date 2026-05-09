package com.lapisdev.vanillacraft.team;

import com.mojang.brigadier.arguments.StringArgumentType;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;

public class TeamModule {
    public static void register(Commands r){
        r.register(Commands.literal("team")
                .then(Commands.literal("create")
                        .then(Commands.argument("name", StringArgumentType.string())
                                .then(Commands.argument("suffix", StringArgumentType.string())
                                        .executes(TeamCreateCmd::execute))))
                .then(Commands.literal("disband")
                        .executes(TeamDisbandCmd::execute))
                .then(Commands.literal("invite")
                        .then(Commands.argument("player", ArgumentTypes.player())
                                .executes(TeamInviteCmd::execute)))
                .then(Commands.literal("leave")
                        .executes(TeamLeaveCmd::execute))
                .build());
    }
}
