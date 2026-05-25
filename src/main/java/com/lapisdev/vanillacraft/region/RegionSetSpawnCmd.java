package com.lapisdev.vanillacraft.region;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class RegionSetSpawnCmd {
    public static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        Player mcplayer = (Player) ctx.getSource().getExecutor();
        ServerPlayer player = ServerPlayer.fromMinecraftUuid(mcplayer.getUniqueId());
        Region region = (PlayerRegion.fromPlayer(player)).region;
        ServerPlayer leader = region.leader;

        if (leader.id == player.id){
            (region.spawn).set(ctx.getArgument("x coordinate", Integer.class), ctx.getArgument("y coordinate", Integer.class), ctx.getArgument("z coordinate", Integer.class));
            region.save();
            mcplayer.sendMessage(Component.text("You have now set a new spawn for your region", NamedTextColor.GREEN));
            return 1;
        }
        else{
            mcplayer.sendMessage(Component.text("You are not a leader of a region", NamedTextColor.RED));
            return 0;
        }
    }
}
