package com.lapisdev.vanillacraft.team;

import com.mojang.brigadier.arguments.StringArgumentType;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

import static com.lapisdev.vanillacraft.VanillacraftPlugin.handle;
import static com.lapisdev.vanillacraft.VanillacraftPlugin.plugin;

public class TeamModule {
    public TeamModule() {
        plugin().getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, (registry) -> {
            TeamModule.registerCommands(registry.registrar());
        });
        handle(new TeamInviteJoinListener());
    }

    public static void registerCommands(Commands r){
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
                .then(Commands.literal("accept")
                        .then(Commands.argument("name", StringArgumentType.string())
                                .executes(TeamAcceptCmd::execute)))
                .then(Commands.literal("reject")
                        .then(Commands.argument("name", StringArgumentType.string())
                                .executes(TeamRejectCmd::execute)))
                .build());
    }
}
