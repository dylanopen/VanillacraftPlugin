package com.lapisdev.vanillacraft.other;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import io.papermc.paper.command.brigadier.Commands;

public class CommandRegistry {
    public static void register(Commands r){
        r.register(Commands.literal("spectate")
                .executes(SpectateCmd::execute)
                .build());

        r.register(Commands.literal("minecartspeed")
                .then(Commands.argument("multiplier", IntegerArgumentType.integer())
                        .executes(MinecartSpeedCmd::execute))
                        .build());
    }
}

