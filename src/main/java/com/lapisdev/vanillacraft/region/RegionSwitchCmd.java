package com.lapisdev.vanillacraft.region;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.lapisdev.vanillacraft.database.Query.sqlUpdate;

public class RegionSwitchCmd {
    public static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        Player mcplayer = (Player) ctx.getSource().getExecutor();
        ServerPlayer player = ServerPlayer.fromMinecraftUuid(mcplayer.getUniqueId());
        Region region = (PlayerRegion.fromPlayer(player)).region;
        ServerPlayer leader = region.leader;
        PlayerRegion playerRegion = PlayerRegion.fromPlayer(player);

        String newRegionName = ctx.getArgument("region name", String.class);
        Region newRegion = Region.fromRegionName(newRegionName);

        if (player.id == leader.id){
            mcplayer.sendMessage(Component.text("You cannot switch regions as you are a leader", NamedTextColor.RED));
            return 0;
        }
        LocalDate givenDate = playerRegion.date.toLocalDate();
        LocalDate currentDate = LocalDate.now();
        if (!(ChronoUnit.WEEKS.between(givenDate, currentDate) >= 1)){
            mcplayer.sendMessage(Component.text("It hasn't been a week since you last switched regions", NamedTextColor.RED));
            return 0;
        }
        else {
            Date date = Date.valueOf(currentDate);
            sqlUpdate("update player_region set region_id = ?, set switch_date = ?, where player_id = ?", region.id, date, player.id);
            playerRegion.save();
            mcplayer.sendMessage(Component.text("You have joined the " + newRegion.name.toUpperCase() + " region", NamedTextColor.GREEN));
            return 1;
        }
    }
}
