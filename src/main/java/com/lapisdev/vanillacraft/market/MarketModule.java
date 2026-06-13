package com.lapisdev.vanillacraft.market;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

import static com.lapisdev.vanillacraft.VanillacraftPlugin.handle;
import static com.lapisdev.vanillacraft.VanillacraftPlugin.plugin;

public class MarketModule {
    public MarketModule() {
        plugin().getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, (registry) -> {
            MarketCmd.register(registry.registrar());
        });
        handle(new MarketListener());
    }
}
