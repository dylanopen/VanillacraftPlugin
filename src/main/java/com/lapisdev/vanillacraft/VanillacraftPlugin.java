package com.lapisdev.vanillacraft;

import com.lapisdev.vanillacraft.database.Database;
import com.lapisdev.vanillacraft.database.DatabaseTables;
import com.lapisdev.vanillacraft.discord.DiscordModule;
import com.lapisdev.vanillacraft.kick.KickModule;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

public final class VanillacraftPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Database.init();
        new DiscordModule();
        new KickModule();
    }

    @Override
    public void onDisable() {
        Database.close();
    }

    public static VanillacraftPlugin plugin() {
        return (VanillacraftPlugin)Bukkit.getServer().getPluginManager().getPlugin("VanillacraftPlugin");
    }

    public static void async(Consumer<ScheduledTask> consumer) {
        Bukkit.getServer().getAsyncScheduler().runNow(plugin(), consumer);
    }
}
