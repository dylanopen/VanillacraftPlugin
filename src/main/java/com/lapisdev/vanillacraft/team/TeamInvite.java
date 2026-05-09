package com.lapisdev.vanillacraft.team;

import com.lapisdev.vanillacraft.player.ServerPlayer;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public void save() {
        sqlUpdateOrInsert("update team_invite set player_id = ?, team_id = ?",
                "insert into team_invite (player_id, team_id) values (?, ?)",
                player, team, id);
    }

    public void delete(){
        sqlDelete("delete from team_invite where team_invite_id = ?", id);
    }

    private static @Nullable TeamInvite fromResultSet(ResultSet rs) {
        try {
            if (!rs.next()) return null;
            TeamInvite teamInvite = new TeamInvite();
            teamInvite.id = rs.getInt("team_invite_id");
            teamInvite.player = ServerPlayer.fromId(rs.getInt("player_id"));
            teamInvite.team = Team.fromTeamId(rs.getInt("team_id"));
            return teamInvite;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
