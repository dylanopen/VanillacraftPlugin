package com.lapisdev.vanillacraft.vanish;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.staff.PlayerStaffRole;
import com.lapisdev.vanillacraft.staff.StaffRole;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static com.lapisdev.vanillacraft.VanillacraftPlugin.plugin;

public class VanishCmd {
    public static void register(Commands commands) {
        commands.register(Commands.literal("vanish")
		.executes(VanishCmd::execute)
                .build());
    }

    private static int execute(CommandContext<CommandSourceStack> ctx) {
	if (!(ctx.getSource().getExecutor() instanceof Player mcExecutor)) {
	    ctx.getSource().getSender().sendMessage("You must be a player to (un)vanish yourself!");
	    return 0;
	}
	ServerPlayer executor = ServerPlayer.fromMinecraftUuid(mcExecutor.getUniqueId());
	    
	boolean isVanished = VanishModule.vanishedPlayers.contains(executor.id);
	if (isVanished) {
	    VanishModule.vanishedPlayers.remove(executor.id);
	    mcExecutor.sendMessage("You are no longer in vanish.");
	} else {
	    VanishModule.vanishedPlayers.add(executor.id);
	    mcExecutor.sendMessage("You are now in vanish.");
	}

	for (Player mcPlayer : Bukkit.getOnlinePlayers()) {
	    ServerPlayer player = ServerPlayer.fromMinecraftUuid(mcPlayer.getUniqueId());
	    boolean isStaff = false;
	    StaffRole roleStaff = StaffRole.fromName("staff");

	    for (PlayerStaffRole staffRole : PlayerStaffRole.fromPlayer(player)) {
		if (staffRole.staffRole.id == roleStaff.id) continue;
	    }
	    if (isStaff) continue;

	    if (isVanished) mcPlayer.hidePlayer(plugin(), mcPlayer);
	    else mcPlayer.showPlayer(plugin(), mcPlayer);
	}
        return 1;
    }
}
