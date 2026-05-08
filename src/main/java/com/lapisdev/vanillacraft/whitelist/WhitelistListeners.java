package com.lapisdev.vanillacraft.whitelist;

import static com.lapisdev.vanillacraft.VanillacraftPlugin.handle;

public class WhitelistListeners {
    public static void register() {
        handle(new WhitelistJoinListener());
    }
}
