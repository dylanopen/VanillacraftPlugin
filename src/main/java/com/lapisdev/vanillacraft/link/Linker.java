package com.lapisdev.vanillacraft.link;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.task.RunTask;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Linker {
    // Returns `true` if the discord account is not already linked, `false` if it was linked to a different account before.
    public static boolean link(String discordUuid, UUID minecraftUuid) {
        ServerPlayer player = ServerPlayer.fromDiscordUuid(discordUuid);
        boolean hasExistingLink = true;
        if (player == null) {
            hasExistingLink = false;
            player = new ServerPlayer();
        } else {
            final UUID oldMinecraftUuid = player.minecraftUuid;
            RunTask.sync(() -> {
                Player kickedPlayer = Bukkit.getPlayer(oldMinecraftUuid);
                if (kickedPlayer == null) return;
                kickedPlayer.kick(Component.text("You have linked your Discord account to another Minecraft account, therefore this Minecraft account can no longer be used."));
            });
        }
        player.discordUuid = discordUuid;
        player.minecraftUuid = minecraftUuid;
        player.save();
        return hasExistingLink;
    }
}
