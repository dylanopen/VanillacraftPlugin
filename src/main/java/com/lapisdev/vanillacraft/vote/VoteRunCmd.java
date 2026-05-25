package com.lapisdev.vanillacraft.vote;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.region.PlayerRegion;
import com.lapisdev.vanillacraft.region.Region;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class VoteRunCmd {
    public static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        Player mcplayer = (Player) ctx.getSource().getExecutor();
        ServerPlayer player = ServerPlayer.fromMinecraftUuid(mcplayer.getUniqueId());
        Region region = PlayerRegion.fromPlayer(player).region;

        if (region == null){
            mcplayer.sendMessage(Component.text("You are not in a region", NamedTextColor.RED));
            return 0;
        }

        mcplayer.sendMessage(Component.text("You have become a candidate for the " + region.name + " region", NamedTextColor.GREEN));
        Candidate candidate = new Candidate(player, region);
        candidate.save();
        return 1;
    }
}
