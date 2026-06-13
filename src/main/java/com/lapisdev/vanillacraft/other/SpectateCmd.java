package com.lapisdev.vanillacraft.other;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SpectateCmd {
    private static HashMap<Player, Location> playerLocations = new HashMap<>();

    public static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        Player player = (Player) ctx.getSource().getSender();

        if (player.getGameMode() == GameMode.SURVIVAL){
            Location location = player.getLocation();
            playerLocations.put(player, location);
            player.setGameMode(GameMode.SPECTATOR);
            return 1;
        }
        else if (player.getGameMode() == GameMode.SPECTATOR){
            Location tpLocation = playerLocations.get(player);
            player.teleport(tpLocation);
            player.setGameMode(GameMode.SURVIVAL);
            return 1;
        }

        else{
            player.sendMessage(Component.text("You should not be in that gamemode", NamedTextColor.RED));
            return 0;
        }
    }
}
