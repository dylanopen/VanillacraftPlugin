package com.lapisdev.vanillacraft.vanish;

import static com.lapisdev.vanillacraft.VanillacraftPlugin.plugin;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

public class VanishCommandRegistry {
    public static void register() {
        plugin().getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, (registry) -> {
	    VanishCmd.register(registry.registrar());
	});
    }
}
