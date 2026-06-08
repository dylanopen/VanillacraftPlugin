package com.lapisdev.vanillacraft.other;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;

public class MinecartSpeedCmd {
    public static double minecartSpeed = 0.4D;

    public static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        int speedMultiplier = ctx.getArgument("multiplier", Integer.class);
        if (speedMultiplier >= 10){
            speedMultiplier = 10;
        }
        minecartSpeed = speedMultiplier * 0.4D;
        return 1;
    }
}
