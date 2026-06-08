package com.lapisdev.vanillacraft.team;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

public class TeamInviteJoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        ServerPlayer player = ServerPlayer.fromMinecraftUuid(e.getPlayer().getUniqueId());
        ArrayList<TeamInvite> teamInvites = TeamInvite.fromPlayer(player);
        for (TeamInvite teamInvite : teamInvites) {
            e.getPlayer().sendMessage(Component.text("You have been invited to team '" + teamInvite.team.name + "'!" +
                    "\nRun /team accept " + teamInvite.team.name + " to join, or /team reject " + teamInvite.team.name + " to decline."));
        }
    }
}
