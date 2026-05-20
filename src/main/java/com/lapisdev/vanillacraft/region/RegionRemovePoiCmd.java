package com.lapisdev.vanillacraft.region;

import com.lapisdev.vanillacraft.database.Query;
import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class RegionRemovePoiCmd {
    public static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        Player mcplayer = (Player) ctx.getSource().getExecutor();
        ServerPlayer player = ServerPlayer.fromMinecraftUuid(mcplayer.getUniqueId());
        Region region = (PlayerRegion.fromPlayer(player)).region;
        ServerPlayer leader = region.leader;

        String targetName = ctx.getArgument("poi name", String.class);

        if (leader.id != player.id) {
            mcplayer.sendMessage(Component.text("You are not a leader of a region"));
            return 0;
        }

        ArrayList<RegionPoi> pois = RegionPoi.fromRegionId(region);

        boolean poiExists = false;
        for (RegionPoi poi : pois) {
            System.out.println(poi.name);
            if (poi.name.equalsIgnoreCase(targetName)) {
                poiExists = true;
            }
        }
        if (!poiExists){
            mcplayer.sendMessage(Component.text("You do not have a POI with that name"));
            return 0;
        }

        Query.sqlDelete("DELETE FROM region_poi WHERE region_id = ? AND region_poi_name = ?", region.id, targetName);
        return 1;
    }
}
