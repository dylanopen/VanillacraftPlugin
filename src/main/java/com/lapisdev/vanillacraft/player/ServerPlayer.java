package com.lapisdev.vanillacraft.player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

import static com.lapisdev.vanillacraft.database.Query.sqlSelect;
import static com.lapisdev.vanillacraft.database.Query.sqlUpdateOrInsert;

public class ServerPlayer {
    public int id;
    public String discordUuid;
    public UUID minecraftUuid;

    public static ServerPlayer fromId(int playerId) {
        return fromResultSet(sqlSelect("select * from player where player_id = ?", playerId));
    }

    public static ServerPlayer fromMinecraftUuid(UUID minecraftUuid) {
        return fromResultSet(sqlSelect("select * from player where minecraft_uuid = ?", minecraftUuid.toString()));
    }

    public static ServerPlayer fromDiscordUuid(String discordUuid) {
        return fromResultSet(sqlSelect("select * from player where discord_uuid = ?", discordUuid));
    }

    public void save() {
        sqlUpdateOrInsert("update player set discord_uuid = ?, minecraft_uuid = ? where player_id = ?",
                "insert into player (discord_uuid, minecraft_uuid) values (?, ?)",
                discordUuid, minecraftUuid.toString(), id);
        // todo: save `id` from key
    }

    private static ServerPlayer fromResultSet(ResultSet rs) {
        try {
            if (!rs.next()) return null;
            ServerPlayer player = new ServerPlayer();
            player.id = rs.getInt("player_id");
            player.discordUuid = rs.getString("discord_uuid");
            player.minecraftUuid = UUID.fromString(rs.getString("minecraft_uuid"));
            return player;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ServerPlayer player)) return false;
        return player.id == this.id;
    }
}
