package com.lapisdev.vanillacraft.region;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class RegionInfoCmd {
    public static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        Player mcplayer = (Player) ctx.getSource().getExecutor();
        ServerPlayer player = ServerPlayer.fromMinecraftUuid(mcplayer.getUniqueId());
        PlayerRegion playerRegion = PlayerRegion.fromPlayer(player);
        Region region = playerRegion.region;
        ServerPlayer leader = region.leader;
        OfflinePlayer mcleader = Bukkit.getOfflinePlayer(leader.minecraftUuid);

        mcplayer.sendMessage(Component.text(region.name.toUpperCase() + " Region Information:"));
        mcplayer.sendMessage(Component.text("Leader: " + mcleader.getName()));
        mcplayer.sendMessage(Component.text("Spawn: " + (int) (region.spawn).getX() + ", " + (int) (region.spawn).getY() + ", " + (int) (region.spawn).getZ()));

        ArrayList<RegionPoi> pois = RegionPoi.fromRegionId(region);

        for (RegionPoi poi : pois) {
            String poiFormatting = poi.name + ": " + poi.x + ", " + poi.y + ", " + poi.z;
            mcplayer.sendMessage(Component.text(poiFormatting));
        }

        return 1;
    }
}
