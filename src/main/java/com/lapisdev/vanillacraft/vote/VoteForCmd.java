package com.lapisdev.vanillacraft.vote;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.region.PlayerRegion;
import com.lapisdev.vanillacraft.region.Region;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class VoteForCmd {
    public static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        Player mcplayer = (Player) ctx.getSource().getExecutor();
        ServerPlayer player = ServerPlayer.fromMinecraftUuid(mcplayer.getUniqueId());
        Region region = (PlayerRegion.fromPlayer(player)).region;


        String candidateName = ctx.getArgument("name", String.class);
        OfflinePlayer bukkitPlayer = Bukkit.getOfflinePlayer(candidateName);
        ServerPlayer candidate = ServerPlayer.fromMinecraftUuid(bukkitPlayer.getUniqueId());

//        LocalDate currentDate = LocalDate.now();
//        int day = currentDate.getDayOfMonth();
//
//
//        if (day == 1){
//            if (candidate )
//
//
//            Vote vote = new Vote(player, region, candidate);
//            if (vote == null) {
//
//            }
//            vote.save();

//        }
//        else {
//            mcplayer.sendMessage(Component.text("There is currently no vote at the moment, the next vote is in " + timeToNextVote));
//            return 0;
//        }


        return 1;
    }
}
