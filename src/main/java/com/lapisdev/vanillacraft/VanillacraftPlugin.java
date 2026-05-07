package com.lapisdev.vanillacraft;

import com.lapisdev.vanillacraft.discord.DiscordModule;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class VanillacraftPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        new DiscordModule();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static VanillacraftPlugin plugin() {
        return (VanillacraftPlugin)Bukkit.getServer().getPluginManager().getPlugin("VanillacraftPlugin");
    }
}
