package com.lapisdev.vanillacraft.team;

import com.lapisdev.vanillacraft.player.ServerPlayer;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.lapisdev.vanillacraft.database.Query.*;

public class Team {
    public int id;
    public String name;
    public String suffix;
    public ServerPlayer leader;

    public static @Nullable Team fromTeamId(int targetTeamId) {
        return fromResultSet(sqlSelect("select * from team where team_id = ?", targetTeamId));
    }

    public static @Nullable Team fromTeamName(String targetTeamName) {
        return fromResultSet(sqlSelect("select * from team where team_name = ?", targetTeamName));
    }

    public static @Nullable Team fromTeamLeader(ServerPlayer targetTeamLeader) {
        return fromResultSet(sqlSelect("select * from team where team_leader = ?", targetTeamLeader.id));
    }

    public static @Nullable Team fromTeamSuffix(String targetTeamSuffix) {
        return fromResultSet(sqlSelect("select * from team where team_suffix = ?", targetTeamSuffix));
    }

    public static @Nullable Team fromTeamMember(ServerPlayer targetTeamMember){
        return fromResultSet(sqlSelect("select * from player_team where player_id = ?", targetTeamMember));
    }

    public Team() {}

    public Team(String name, String suffix, ServerPlayer leader) {
        this.name = name;
        this.suffix = suffix;
        this.leader = leader;
    }

    public void save() {
        sqlUpdateOrInsert("update team set team_name = ?, leader_id = ? where team_id = ?",
                "insert into team (team_name, team_suffix, leader_id) values (?, ?, ?)",
                name, suffix, leader.id, id);
    }

    public void delete(){
        sqlDelete("delete from team where team_id = ?", id);
        sqlDelete("delete from player_team where team_id = ?", id);
    }

    private static @Nullable Team fromResultSet(ResultSet rs) {
        try {
            if (!rs.next()) return null;
            Team team = new Team();
            team.id = rs.getInt("team_id");
            team.name = rs.getString("team_name");
            team.suffix = rs.getString("team_suffix");
            team.leader = ServerPlayer.fromId(rs.getInt("leader_id"));
            return team;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
