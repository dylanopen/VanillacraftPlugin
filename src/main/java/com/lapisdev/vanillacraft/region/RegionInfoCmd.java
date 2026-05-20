package com.lapisdev.vanillacraft.region;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RegionInfoCmd {
    public static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        Player mcplayer = (Player) ctx.getSource().getExecutor();
        ServerPlayer player = ServerPlayer.fromMinecraftUuid(mcplayer.getUniqueId());
        PlayerRegion playerRegion = PlayerRegion.fromPlayer(player);
        Region region = playerRegion.region;
        ServerPlayer leader = region.leader;
        Player mcleader = Bukkit.getPlayer(leader.minecraftUuid);

        mcplayer.sendMessage(Component.text(region.name + " Region Information:"));
        mcplayer.sendMessage(Component.text("Leader: " + mcleader.getName()));
        mcplayer.sendMessage(Component.text("Spawn: " + (region.spawn).getX() + (region.spawn).getY() + (region.spawn).getZ()));
        for (int i = 0; i < (region.poiLocations).size(); i++){
            mcplayer.sendMessage(Component.text(region.poiLocations.get(i)));
        }

        return 1;
    }
}
