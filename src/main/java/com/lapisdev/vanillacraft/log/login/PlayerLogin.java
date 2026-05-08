package com.lapisdev.vanillacraft.log.login;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.lapisdev.vanillacraft.database.Query.sqlInsert;
import static com.lapisdev.vanillacraft.database.Query.sqlSelect;

public class PlayerLogin {
    public ServerPlayer player;
    public long loginTime;
    public Location location;

    public static @NotNull ArrayList<PlayerLogin> fromPlayer(ServerPlayer player) {
        return fromResultSet(sqlSelect("select * from player_login where player_id = ?", player.id));
    }

    public static @NotNull ArrayList<PlayerLogin> fromLoginTime(long loginTime) {
        return fromResultSet(sqlSelect("select * from player_login where login_time = ?", loginTime));
    }

    public PlayerLogin() {}

    public PlayerLogin(ServerPlayer player, long loginTime) {
        this.player = player;
        this.loginTime = loginTime;
    }

    public PlayerLogin(ServerPlayer player, long loginTime, Location location) {
        this.player = player;
        this.loginTime = loginTime;
        this.location = location;
    }

    public void save() {
        sqlInsert("insert into player_login (player_id, login_time, login_world, login_x, login_y, login_z) values (?, ?, ?, ?, ?, ?)",
                player.id, loginTime, location.getWorld().getName(), location.getX(), location.getY(), location.getZ());
    }

    private static @NotNull ArrayList<PlayerLogin> fromResultSet(ResultSet rs) {
        try {
            ArrayList<PlayerLogin> list = new ArrayList<>();
            while (rs.next()) {
                PlayerLogin pl = new PlayerLogin();
                pl.player = ServerPlayer.fromId(rs.getInt("player_id"));
                pl.loginTime = rs.getLong("login_time");
                pl.location = new Location(
                        Bukkit.getWorld(rs.getString("login_world")),
                        rs.getDouble("login_x"),
                        rs.getDouble("login_y"),
                        rs.getDouble("login_z")
                );
                list.add(pl);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

