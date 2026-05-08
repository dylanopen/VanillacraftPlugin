package com.lapisdev.vanillacraft.link;

import com.lapisdev.vanillacraft.database.Database;
import com.lapisdev.vanillacraft.task.RunTask;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Linker {
    public static void link(String discordUuid, UUID minecraftUuid) {
        try {
            PreparedStatement stmt = Database.conn.prepareStatement("insert or replace into player" +
                    "(player_id, discord_uuid, minecraft_uuid) " +
                    "values ((select player_id from player where discord_uuid = " + discordUuid + "), ?, ?)");
            stmt.setString(1, discordUuid);
            stmt.setString(2, minecraftUuid.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            PreparedStatement stmt = Database.conn.prepareStatement("select player_id from player where discord_uuid = ?");
            stmt.setString(1, discordUuid);
            ResultSet rs = stmt.executeQuery();
            int playerId = rs.getInt("player_id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
