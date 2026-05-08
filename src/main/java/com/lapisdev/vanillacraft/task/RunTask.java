package com.lapisdev.vanillacraft.task;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;

import java.util.function.Consumer;

import static com.lapisdev.vanillacraft.VanillacraftPlugin.plugin;

public class RunTask {
    public static void sync(Runnable runnable) {
        Bukkit.getServer().getScheduler().runTask(plugin(), runnable);
    }

    public static void async(Consumer<ScheduledTask> consumer) {
        Bukkit.getServer().getAsyncScheduler().runNow(plugin(), consumer);
    }
}
