package com.lapisdev.vanillacraft.vanish;

import java.util.ArrayList;

public class VanishModule {
    public static ArrayList<Integer> vanishedPlayers = new ArrayList<>();

    public VanishModule() {
        VanishCommandRegistry.register();
    }
}
