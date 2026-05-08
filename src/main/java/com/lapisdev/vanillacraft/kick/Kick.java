package com.lapisdev.vanillacraft.kick;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Kick {
    ServerPlayer player;
    Component reason;

    public Kick(ServerPlayer player, Component reason) {
        this.player = player;
        this.reason = reason;
    }

    private Component fullReason() {
        return Component.text("You have been kicked from the server.", NamedTextColor.GRAY)
                .append(Component.text("\nReason: ", NamedTextColor.WHITE)).append(reason);
    }

    public boolean execute() {
        Player player = Bukkit.getPlayer(this.player.minecraftUuid);
        if (player == null) return false;
        player.kick(fullReason());
        return true;
    }
}
