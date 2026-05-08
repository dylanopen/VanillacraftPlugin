package com.lapisdev.vanillacraft.whitelist;

import com.lapisdev.vanillacraft.kick.Kick;
import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.region.Region;
import com.lapisdev.vanillacraft.task.RunTask;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player minecraftPlayer = e.getPlayer();
        UUID minecraftUuid = minecraftPlayer.getUniqueId();
        ServerPlayer player = ServerPlayer.fromMinecraftUuid(minecraftUuid);
        if (player == null) {
            ServerPlayer loggedInPlayer = new ServerPlayer();
            loggedInPlayer.minecraftUuid = minecraftUuid;
            Kick kick = new Kick(loggedInPlayer, Component.text("You haven't been whitelisted, or haven't linked your account.\nPlease link using the #get-whitelisted channel on discord!"));
            RunTask.sync(kick::execute);
        }
        // todo: get player's region and check it exists
    }
}
