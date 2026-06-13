package com.lapisdev.vanillacraft.maintenance;

import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class MaintenanceCmdMc {
    public static void register(Commands commands) {
        commands.register(Commands.literal("maintenance")
                .then(Commands.literal("on")
                        .executes(MaintenanceCmdMc::enableMaintenance))
                .then(Commands.literal("off")
                        .executes(MaintenanceCmdMc::disableMaintenance))
                .build());
    }

    private static int enableMaintenance(CommandContext<CommandSourceStack> ctx) {
        ctx.getSource().getSender().sendMessage(Component.text("Maintenance mode enabled.", NamedTextColor.GREEN));
        Maintenance.enable();
        return 1;
    }

    private static int disableMaintenance(CommandContext<CommandSourceStack> ctx) {
        ctx.getSource().getSender().sendMessage(Component.text("Maintenance mode disabled.", NamedTextColor.RED));
        Maintenance.disable();
        return 1;
    }
}
