package com.lapisdev.vanillacraft.vote;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.region.Region;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.lapisdev.vanillacraft.database.Query.sqlSelect;
import static com.lapisdev.vanillacraft.database.Query.sqlUpdateOrInsert;

public class Candidate {
    public ServerPlayer player;
    public Region region;

    public static @Nullable Candidate fromPlayerId(int targetPlayerId) {
        return fromResultSet(sqlSelect("select * from vote where player_id = ?", targetPlayerId)).getFirst();
    }

    public static ArrayList<Candidate> fromRegionId(int targetRegionId) {
        return fromResultSet(sqlSelect("select * from vote where region_id = ?", targetRegionId));
    }

    public Candidate() {
    }

    public Candidate(ServerPlayer player, Region region) {
        this.player = player;
        this.region = region;
    }

    public void save() {
        sqlUpdateOrInsert("update vote set candidate_player_id = ?, region_id = ? where candidate_player_id = ?",
                "insert into candidate (candidate_player_id, region_id) values (?, ?)",
                player.id, region.id, player.id);
    }

    private static ArrayList<Candidate> fromResultSet(ResultSet rs) {
        try {
            ArrayList<Candidate> candidates = new ArrayList<>();
            while (rs.next()) {
                Candidate candidate = new Candidate();
                candidate.player = ServerPlayer.fromId(rs.getInt("candidate_player_id"));
                candidate.region = Region.fromRegionId(rs.getInt("region_id"));
                candidates.add(candidate);
            }
            return candidates;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
