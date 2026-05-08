package com.lapisdev.vanillacraft.region;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.lapisdev.vanillacraft.database.Query.sqlSelect;
import static com.lapisdev.vanillacraft.database.Query.sqlUpdateOrInsert;

public class PlayerRegion {
    public ServerPlayer player;
    public Region region;

    public static ArrayList<PlayerRegion> fromRegionId(Region region) {
        return fromResultSet(sqlSelect("select * from region where region_id = ?", region.id));
    }

    public static @Nullable PlayerRegion fromPlayer(ServerPlayer player) {
        List<PlayerRegion> playerRegions = fromResultSet(sqlSelect("select * from player_region where player_id = ?", player.id));
        return playerRegions.getFirst();
    }

    public PlayerRegion() {}

    public PlayerRegion(ServerPlayer player, Region region) {
        this.player = player;
        this.region = region;
    }

    public void save() {
        sqlUpdateOrInsert("update player_region set player_id = ?, region_id = ? where region_id = ?",
                "insert into player_region (player_id, region_id) values (?, ?)",
                player.id, region.id);
    }

    private static @NotNull ArrayList<PlayerRegion> fromResultSet(ResultSet rs) {
        try {
            ArrayList<PlayerRegion> playerRegions = new ArrayList<>();
            while (rs.next()) {
                PlayerRegion playerRegion = new PlayerRegion();
                playerRegion.player = ServerPlayer.fromId(rs.getInt("player_id"));
                playerRegion.region = Region.fromRegionId(rs.getInt("region_id"));
                playerRegions.add(playerRegion);
            }
            return playerRegions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
