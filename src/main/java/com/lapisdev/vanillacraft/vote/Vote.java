package com.lapisdev.vanillacraft.vote;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import com.lapisdev.vanillacraft.region.Region;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.lapisdev.vanillacraft.database.Query.*;

public class Vote {
    public int id;
    public ServerPlayer player;
    public Region region;
    public ServerPlayer candidate;

    public static @Nullable Vote fromVoteId(int targetVoteId){
        return fromResultSet(sqlSelect("select * from vote where vote_id = ?", targetVoteId));
    }

    public static @Nullable Vote fromPlayerId(int targetPlayerId){
        return fromResultSet(sqlSelect("select * from vote where player_id = ?", targetPlayerId));
    }

    public static @Nullable Vote fromRegionId(int targetRegionId){
        return fromResultSet(sqlSelect("select * from vote where region_id = ?", targetRegionId));
    }

    public static @Nullable Vote fromCandidateId(int targetCandidateId){
        return fromResultSet(sqlSelect("select * from vote where candidate_id = ?", targetCandidateId));
    }

    public Vote() {}

    public Vote(ServerPlayer player, Region region, ServerPlayer candidate){
        this.player = player;
        this.region = region;
        this.candidate = candidate;
    }

    public void save(){
        sqlUpdateOrInsert("update vote set player_id = ?, region_id = ?, candidate_id = ? where vote_id = ?",
                "insert into vote (player_id, region_id, candidate_id) values (?, ?, ?)",
                player, region, candidate, id);
    }

    private static @Nullable Vote fromResultSet(ResultSet rs){
        try{
            if (!rs.next()) return null;
            Vote vote = new Vote();
            vote.id = rs.getInt("vote_id");
            vote.player = ServerPlayer.fromId(rs.getInt("player_id"));
            vote.region = Region.fromRegionId(rs.getInt("region_id"));
            vote.candidate = ServerPlayer.fromId(rs.getInt("candidate_id"));
            return vote;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
