package com.lapisdev.vanillacraft.team;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.lapisdev.vanillacraft.database.Query.sqlSelect;
import static com.lapisdev.vanillacraft.database.Query.sqlUpdateOrInsert;

public class PlayerTeam {
    public ServerPlayer player;
    public Team team;

    public static ArrayList<PlayerTeam> fromTeamId(Team team) {
        return fromResultSet(sqlSelect("select * from team where team_id = ?", team.id));
    }

    public static @Nullable PlayerTeam fromPlayer(ServerPlayer player) {
        List<PlayerTeam> playerTeams = fromResultSet(sqlSelect("select * from player_team where player_id = ?", player.id));
        return playerTeams.getFirst();
    }

    public PlayerTeam() {}

    public PlayerTeam(ServerPlayer player, Team team) {
        this.player = player;
        this.team = team;
    }

    public void save() {
        sqlUpdateOrInsert("update player_team set player_id = ?, team_id = ? where team_id = ?",
                "insert into player_team (player_id, team_id) values (?, ?)",
                player.id, team.id);
    }

    private static @NotNull ArrayList<PlayerTeam> fromResultSet(ResultSet rs) {
        try {
            ArrayList<PlayerTeam> playerTeams = new ArrayList<>();
            while (rs.next()) {
                PlayerTeam playerTeam = new PlayerTeam();
                playerTeam.player = ServerPlayer.fromId(rs.getInt("player_id"));
                playerTeam.team = Team.fromTeamId(rs.getInt("team_id"));
                playerTeams.add(playerTeam);
            }
            return playerTeams;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
