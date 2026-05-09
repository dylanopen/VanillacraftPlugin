package com.lapisdev.vanillacraft.ban;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.lapisdev.vanillacraft.duration.DurationDisplay;
import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.task.RunTask;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.PlayerProfileListResolver;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Collection;
import java.util.Objects;

public class BanCmdMc {
    public static void register(Commands commands) {
        commands.register(Commands.literal("ban")
                .then(Commands.argument("players", ArgumentTypes.playerProfiles())
                        .then(Commands.argument("duration", StringArgumentType.string())
                                .then(Commands.argument("reason", StringArgumentType.greedyString())
                                        .executes(BanCmdMc::executeDurationReason)))
                        .then(Commands.argument("duration", StringArgumentType.string())
                                .executes(BanCmdMc::executeDuration))
//                        .then(Commands.argument("reason", StringArgumentType.string())
//                                .executes(BanCmdMc::executeReason))
                        .executes(BanCmdMc::execute)).build());
    }

    public static int runBan(CommandContext<CommandSourceStack> ctx, long endTime, String reason) {
        Collection<PlayerProfile> profiles = null;
        try {
            profiles = ctx.getArgument("players", PlayerProfileListResolver.class).resolve(ctx.getSource());
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }

        for (PlayerProfile profile : profiles) {
            ServerPlayer player = ServerPlayer.fromMinecraftUuid(Objects.requireNonNull(profile.getId()));
            if (player == null) {
                ctx.getSource().getSender().sendMessage(Component.text("Player not found", NamedTextColor.RED));
                continue;
            }
            new Ban(player, reason, endTime).execute();
            ctx.getSource().getSender().sendMessage(Component.text("Player " + profile.getName() + " has been banned for reason: " + reason, NamedTextColor.GREEN));
        }
        return 1;
    }

    public static int executeDurationReason(CommandContext<CommandSourceStack> ctx) {
        RunTask.async(_ -> {
            long duration = DurationDisplay.fromString(StringArgumentType.getString(ctx, "duration"));
            runBan(ctx, System.currentTimeMillis() + duration, StringArgumentType.getString(ctx, "reason"));
        });
        return 1;
    }

//    public static int executeReason(CommandContext<CommandSourceStack> ctx) {
//        RunTask.async(_ -> {
//            runBan(ctx, Long.MAX_VALUE, StringArgumentType.getString(ctx, "reason"));
//        });
//        return 1;
//    }

    public static int executeDuration(CommandContext<CommandSourceStack> ctx) {
        RunTask.async(_ -> {
            long duration = DurationDisplay.fromString(StringArgumentType.getString(ctx, "duration"));
            runBan(ctx, System.currentTimeMillis() + duration, "No reason provided.");
        });
        return 1;
    }

    public static int execute(CommandContext<CommandSourceStack> ctx) {
        RunTask.async(_ -> {
            runBan(ctx, Long.MAX_VALUE, "No reason provided.");
        });
        return 1;
    }
}
