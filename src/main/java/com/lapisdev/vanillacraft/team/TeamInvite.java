package com.lapisdev.vanillacraft.team;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.region.PlayerRegion;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.lapisdev.vanillacraft.database.Query.*;

public class TeamInvite {
    public int id;
    public ServerPlayer player;
    public Team team;

    public TeamInvite() {}

    public TeamInvite(ServerPlayer player, Team team) {
        this.player = player;
        this.team = team;
    }

    public static @Nullable ArrayList<TeamInvite> fromPlayer(ServerPlayer player) {
        return fromResultSet(sqlSelect("select * from team_invite where player_id = ?", player.id));
    }

    public void save() {
        sqlUpdateOrInsert("update team_invite set player_id = ?, team_id = ? where team_invite_id = ?",
                "insert into team_invite (player_id, team_id) values (?, ?)",
                player.id, team.id, id);
    }

    public void delete() {
        sqlDelete("delete from team_invite where team_invite_id = ?", id);
    }

    public static boolean playerHasInvite(ServerPlayer player, Team team) {
        try {
            return sqlSelect("select team_invite_id from team_invite where player_id = ? and team_id = ?", player.id, team.id).next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static @Nullable ArrayList<TeamInvite> fromResultSet(ResultSet rs) {
        try {
            ArrayList<TeamInvite> teamInvites = new ArrayList<>();
            while (rs.next()) {
                TeamInvite teamInvite = new TeamInvite();
                teamInvite.id = rs.getInt("team_invite_id");
                teamInvite.player = ServerPlayer.fromId(rs.getInt("player_id"));
                teamInvite.team = Team.fromTeamId(rs.getInt("team_id"));
                teamInvites.add(teamInvite);
            }
            return teamInvites;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
