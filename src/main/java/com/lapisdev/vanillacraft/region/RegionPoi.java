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

public class RegionPoi {
    public int id = 0;
    public Region region;
    public int x;
    public int y;
    public int z;
    public String name;

    public static @Nullable ArrayList<RegionPoi> fromRegionId(Region region) {
        return fromResultSet(sqlSelect("select * from region_poi where region_id = ?", region.id));
    }

    public RegionPoi() {}

    public RegionPoi(Region region, int x, int y, int z, String name) {
        this.region = region;
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public void save() {
        sqlUpdateOrInsert("update region_poi set region_id = ?, region_poi_x = ?, region_poi_y = ?, region_poi_z = ?, region_poi_name = ?  where region_id = ?",
                "insert into region_poi (region_id, region_poi_x, region_poi_y, region_poi_z, region_poi_name) values (?, ?, ?, ?, ?)",
                region.id, x, y, z, name, id);
    }

    private static @NotNull ArrayList<RegionPoi> fromResultSet(ResultSet rs) {
        try {
            ArrayList<RegionPoi> playerRegions = new ArrayList<>();
            while (rs.next()) {
                RegionPoi playerRegion = new RegionPoi();
                playerRegion.region = Region.fromRegionId(rs.getInt("region_id"));
                playerRegion.x = rs.getInt("region_poi_x");
                playerRegion.y = rs.getInt("region_poi_y");
                playerRegion.z = rs.getInt("region_poi_z");
                playerRegion.name = rs.getString("region_poi_name");
                playerRegions.add(playerRegion);
            }
            return playerRegions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
