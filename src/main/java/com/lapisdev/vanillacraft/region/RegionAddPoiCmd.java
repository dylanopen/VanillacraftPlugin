package com.lapisdev.vanillacraft.region;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class RegionAddPoiCmd {
    public static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        Player mcplayer = (Player) ctx.getSource().getExecutor();
        ServerPlayer player = ServerPlayer.fromMinecraftUuid(mcplayer.getUniqueId());
        Region region = (PlayerRegion.fromPlayer(player)).region;
        ServerPlayer leader = region.leader;

        System.out.println(region.name + ", leader = " + region.leader.id + ", executed by " + player.id);

        ArrayList<RegionPoi> pois = RegionPoi.fromRegionId(region);
        if (leader.id == player.id){
            if (pois.size() <= 5){
                RegionPoi poi = new RegionPoi(region, ctx.getArgument("x coordinate", Integer.class), ctx.getArgument("y coordinate", Integer.class), ctx.getArgument("z coordinate", Integer.class), ctx.getArgument("point of interest name", String.class));
                poi.save();
                mcplayer.sendMessage(Component.text("You have added a new POI to your region", NamedTextColor.GREEN));
                return 1;
            }
            else {
                mcplayer.sendMessage(Component.text("You already have 5 POIs marked, use /region removepoi if you want to change any of them", NamedTextColor.RED));
                return 0;
            }
        }
        else{
            mcplayer.sendMessage(Component.text("You are not a leader of a region", NamedTextColor.RED));
            return 0;
        }
    }
}
