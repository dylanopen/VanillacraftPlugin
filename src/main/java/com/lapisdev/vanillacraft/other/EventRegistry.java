package com.lapisdev.vanillacraft.other;

import com.lapisdev.vanillacraft.VanillacraftPlugin;
import org.bukkit.event.Listener;

public class EventRegistry {
    public static void handle(Listener listener){
        VanillacraftPlugin.plugin.getServer().getPluginManager().registerEvents(listener, VanillacraftPlugin.plugin);
    }

    public static void register() {
        handle(new PlayerMinecartEvent());
    }
}
