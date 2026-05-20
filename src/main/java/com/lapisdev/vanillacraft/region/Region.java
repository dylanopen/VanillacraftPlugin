package com.lapisdev.vanillacraft.region;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.lapisdev.vanillacraft.database.Query.sqlSelect;
import static com.lapisdev.vanillacraft.database.Query.sqlUpdateOrInsert;

public class Region {
    public int id;
    public String name;
    public Location spawn;
    public ServerPlayer leader;
    public List<String> poiLocations;

    public static @Nullable Region fromRegionId(int regionId) {
        return fromResultSet(sqlSelect("select * from region where region_id = ?", regionId));
    }

    public static @Nullable Region fromRegionName(String regionName) {
        return fromResultSet(sqlSelect("select * from region where region_name = ?", regionName));
    }

    public static @Nullable Region fromLeader(ServerPlayer leader) {
        return fromResultSet(sqlSelect("select * from region where leader_id = ?", leader.id));
    }

    public Region() {}

    public Region(String name, Location spawn, ServerPlayer leader) {
        this.name = name;
        this.spawn = spawn;
        this.leader = leader;
    }

    public void save() {
        sqlUpdateOrInsert("update region set region_name = ?, spawn_world = ?, spawn_x = ?, spawn_y = ?, spawn_z = ?, leader_id = ? where region_id = ?",
                "insert into region (region_name, spawn_world, spawn_x, spawn_y, spawn_z, leader_id) values (?, ?, ?, ?, ?, ?)",
                name, spawn.getWorld().getName(), spawn.getX(), spawn.getY(), spawn.getZ(), leader.id, id);
    }

    private static @Nullable Region fromResultSet(ResultSet rs) {
        try {
            if (!rs.next()) return null;
            Region region = new Region();
            region.id = rs.getInt("region_id");
            region.name = rs.getString("region_name");
            region.spawn = new Location(
                    Bukkit.getWorld("world"),
                    rs.getDouble("spawn_x"),
                    rs.getDouble("spawn_y"),
                    rs.getDouble("spawn_z")
            );
            region.leader = ServerPlayer.fromId(rs.getInt("leader_id"));
            return region;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
