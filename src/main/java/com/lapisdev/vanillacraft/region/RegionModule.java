package com.lapisdev.vanillacraft.region;

import com.lapisdev.vanillacraft.team.*;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

import static com.lapisdev.vanillacraft.VanillacraftPlugin.plugin;

public class RegionModule {
    public RegionModule() {
        plugin().getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, (registry) -> {
            RegionModule.registerCommands(registry.registrar());
        });
    }

    public static void registerCommands(Commands r){
        r.register(Commands.literal("region")
                .then(Commands.literal("info")
                        .executes(RegionInfoCmd::execute))
                .then(Commands.literal("setspawn")
                        .then(Commands.argument("x coordinate", IntegerArgumentType.integer())
                                .then(Commands.argument("y coordinate", IntegerArgumentType.integer())
                                        .then(Commands.argument("z coordinate", IntegerArgumentType.integer())
                                                .executes(RegionSetSpawnCmd::execute)))))
                .then(Commands.literal("addpoi")
                        .then(Commands.argument("point of interest name", StringArgumentType.string())
                                .then(Commands.argument("x coordinate", IntegerArgumentType.integer())
                                        .then(Commands.argument("y coordinate", IntegerArgumentType.integer())
                                                .then(Commands.argument("z coordinate", IntegerArgumentType.integer())
                                                        .executes(RegionAddPoiCmd::execute))))))
                .then(Commands.literal("removepoi")
                        .then(Commands.argument("poi name", StringArgumentType.string())
                                .executes(RegionRemovePoiCmd::execute)))
                .build());
    }
}
