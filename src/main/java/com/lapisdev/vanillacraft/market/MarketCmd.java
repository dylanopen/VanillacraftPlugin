package com.lapisdev.vanillacraft.market;

import com.lapisdev.vanillacraft.task.RunTask;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.Comparator;

public class MarketCmd {
    public static void register(Commands commands) {
        commands.register(Commands.literal("market")
                .then(Commands.argument("material", StringArgumentType.string())
                        .executes(MarketCmd::executeSearch))
                .executes(MarketCmd::executeList).build());
    }

    public static int executeList(CommandContext<CommandSourceStack> ctx) {
        RunTask.async(_ -> {
            ArrayList<ShopItem> items = ShopItem.getAll();
            items.removeIf(item -> item.stock < item.quantity);
            items.sort(Comparator.comparingDouble(item -> (double) item.price / item.quantity));

            ctx.getSource().getSender().sendMessage(Component.text("--- Market ---", NamedTextColor.GOLD));
            if (items.isEmpty()) {
                ctx.getSource().getSender().sendMessage(Component.text("No shops found with enough stock.", NamedTextColor.RED));
            } else {
                for (ShopItem item : items) {
                    ctx.getSource().getSender().sendMessage(formatShopItem(item));
                }
            }
        });
        return 1;
    }

    public static int executeSearch(CommandContext<CommandSourceStack> ctx) {
        String materialName = StringArgumentType.getString(ctx, "material").toUpperCase();
        RunTask.async(_ -> {
            ArrayList<ShopItem> items = ShopItem.getAll();
            items.removeIf(item -> !item.material.name().equalsIgnoreCase(materialName) || item.stock < item.quantity);
            items.sort(Comparator.comparingDouble(item -> (double) item.price / item.quantity));

            ctx.getSource().getSender().sendMessage(Component.text("--- Market: " + materialName + " ---", NamedTextColor.GOLD));
            if (items.isEmpty()) {
                ctx.getSource().getSender().sendMessage(Component.text("No shops found with enough stock.", NamedTextColor.RED));
            } else {
                for (ShopItem item : items) {
                    ctx.getSource().getSender().sendMessage(formatShopItem(item));
                }
            }
        });
        return 1;
    }

    private static Component formatShopItem(ShopItem item) {
        return Component.text(item.material.name() + ": ", NamedTextColor.YELLOW)
                .append(Component.text(item.quantity + " for " + item.price + " ", NamedTextColor.WHITE))
                .append(Component.text("(Stock: " + item.stock + ") ", NamedTextColor.GRAY))
                .append(Component.text("@ " + item.location.getBlockX() + " " + item.location.getBlockY() + " " + item.location.getBlockZ(), NamedTextColor.AQUA));
    }
}
