package com.lapisdev.vanillacraft.region;

import com.lapisdev.vanillacraft.database.Database;
import org.bukkit.Location;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Region {
    public int id;
    public String name;
    public Location spawn;
    public ServerPlayer leader;

    public static @Nullable Region fromRegionId(int targetRegionId) {
        try {
            PreparedStatement stmt = Database.conn.prepareStatement("select * from region where region_id = ?");
            stmt.setInt(1, targetRegionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Region region = new Region();
                region.id = rs.getInt("region_id");
                String regionName = rs.getString("region_name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
