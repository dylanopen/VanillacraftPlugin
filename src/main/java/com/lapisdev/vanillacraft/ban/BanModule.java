package com.lapisdev.vanillacraft.ban;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

import static com.lapisdev.vanillacraft.VanillacraftPlugin.handle;
import static com.lapisdev.vanillacraft.VanillacraftPlugin.plugin;

public class BanModule {
    public BanModule() {
        plugin().getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, (registry) -> {
            BanCmdMc.register(registry.registrar());
        });
        handle(new BanJoinListener());
    }
}
