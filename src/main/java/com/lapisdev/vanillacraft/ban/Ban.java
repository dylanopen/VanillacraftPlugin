package com.lapisdev.vanillacraft.ban;

import com.lapisdev.vanillacraft.kick.Kick;
import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.task.RunTask;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.lapisdev.vanillacraft.database.Query.sqlInsert;
import static com.lapisdev.vanillacraft.database.Query.sqlSelect;

public class Ban {
    public int id;
    public ServerPlayer player;
    public String reason;
    public long endTime;

    public Ban(ServerPlayer player, String reason, long endTime) {
        this.player = player;
        this.reason = reason;
        this.endTime = endTime;
    }

    public Ban(ServerPlayer player, String reason) {
        this.player = player;
        this.reason = reason;
        this.endTime = Long.MAX_VALUE;
    }

    public Ban() {}

    public void execute() {
        RunTask.sync(() -> new Kick(player, Component.text("You have been banned. Please reconnect for details.")).execute());
        save();
    }

    public static ArrayList<Ban> fromPlayer(ServerPlayer player) {
        return fromResultSet(sqlSelect("select * from ban where player_id = ? order by ban_end_time asc", player.id));
    }

    public static ArrayList<Ban> activeFromPlayer(ServerPlayer player) {
        return fromResultSet(sqlSelect("select * from ban where player_id = ? and ban_end_time > ? order by ban_end_time asc",
                player.id, System.currentTimeMillis()
        ));
    }

    public static ArrayList<Ban> getAll() {
        return fromResultSet(sqlSelect("select * from ban order by ban_end_time asc"));
    }

    public static ArrayList<Ban> getActiveBans() {
        return fromResultSet(sqlSelect("select * from ban where ban_end_time > ? order by ban_end_time asc", System.currentTimeMillis()));
    }

    public void save() {
        sqlInsert("insert into ban (player_id, ban_reason, ban_end_time) values (?, ?, ?)",
                player.id, reason, endTime);
    }

    private static @NotNull ArrayList<Ban> fromResultSet(ResultSet rs) {
        try {
            ArrayList<Ban> bans = new ArrayList<>();
            while (rs.next()) {
                Ban ban = new Ban();
                ban.player = ServerPlayer.fromId(rs.getInt("player_id"));
                ban.reason = rs.getString("ban_reason");
                ban.endTime = rs.getLong("ban_end_time");
                bans.add(ban);
            }
            return bans;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
