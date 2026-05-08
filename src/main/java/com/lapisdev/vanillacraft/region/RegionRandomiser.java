package com.lapisdev.vanillacraft.region;

import com.lapisdev.vanillacraft.player.ServerPlayer;

import java.util.Random;

public class RegionRandomiser {
    public static Region getRandomRegion() {
        Random random = new Random();
        int regionId = random.nextInt(1, 5);
        return Region.fromRegionId(regionId);
    }

    public static String placeInRandomRegion(ServerPlayer player) {
        Region region = getRandomRegion();
        PlayerRegion playerRegion = new PlayerRegion(player, region);
        playerRegion.save();
        return region.name;
    }
}
