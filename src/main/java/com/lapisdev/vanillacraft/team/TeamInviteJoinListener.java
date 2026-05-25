package com.lapisdev.vanillacraft.team;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
            Team team = Team.fromTeamId(teamInvite.id);
            e.getPlayer().sendMessage(Component.text("You have been invited to team '" + team.name + "'!" +
                    "\nRun /team accept " + team.name + " to join, or /team reject " + team.name + " to decline.", NamedTextColor.GREEN));
        }
    }
}
