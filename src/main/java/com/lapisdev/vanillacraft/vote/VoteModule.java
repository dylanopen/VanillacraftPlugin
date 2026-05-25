package com.lapisdev.vanillacraft.vote;

import com.mojang.brigadier.arguments.StringArgumentType;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

import static com.lapisdev.vanillacraft.VanillacraftPlugin.plugin;

public class VoteModule {
    public VoteModule() {
        plugin().getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, (registry) -> {
            VoteModule.registerCommands(registry.registrar());
        });
    }

    public static void registerCommands(Commands r){
        r.register(Commands.literal("vote")
                .then(Commands.literal("for")
                        .then(Commands.argument("name", StringArgumentType.string())
                                .executes(VoteForCmd::execute)))
                .then(Commands.literal("run")
                                .executes(VoteRunCmd::execute))
                .build());
    }
}
