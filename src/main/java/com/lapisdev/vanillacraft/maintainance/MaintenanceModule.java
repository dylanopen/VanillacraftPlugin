package com.lapisdev.vanillacraft.maintainance;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

import static com.lapisdev.vanillacraft.VanillacraftPlugin.handle;
import static com.lapisdev.vanillacraft.VanillacraftPlugin.plugin;
import static com.lapisdev.vanillacraft.discord.Discord.jda;

public class MaintenanceModule {
    public MaintenanceModule() {
        plugin().getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, (registry) -> {
            MaintenanceCmdMc.register(registry.registrar());
        });
        handle(new MaintenanceJoinListener());

        jda.addEventListener(new MaintenanceCmdDc());
    }
}
