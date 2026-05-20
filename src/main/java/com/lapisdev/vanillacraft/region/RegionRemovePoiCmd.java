package com.lapisdev.vanillacraft.region;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class RegionRemovePoiCmd {
    public static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        Player mcplayer = (Player) ctx.getSource().getExecutor();
        ServerPlayer player = ServerPlayer.fromMinecraftUuid(mcplayer.getUniqueId());
        Region region = (PlayerRegion.fromPlayer(player)).region;
        ServerPlayer leader = region.leader;

        int index = ctx.getArgument("poi index", Integer.class);

        if (leader == player){
            if ((region.poiLocations).get(index) != null){
                mcplayer.sendMessage(Component.text("You have removed " + (region.poiLocations).get(index) + " from your POI list"));
                (region.poiLocations).remove(index);
                region.save();
                return 1;
            }
            else{
                mcplayer.sendMessage(Component.text("You do not have a POI at that index"));
                return 0;
            }
        }
        else {
            mcplayer.sendMessage(Component.text("You are not a leader of a region"));
            return 0;
        }
    }
}
