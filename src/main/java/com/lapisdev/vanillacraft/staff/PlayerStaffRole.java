package com.lapisdev.vanillacraft.staff;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.lapisdev.vanillacraft.database.Query.*;

public class PlayerStaffRole {
    public ServerPlayer player;
    public StaffRole staffRole;

    public static @NotNull ArrayList<PlayerStaffRole> fromStaffRoleId(StaffRole staffRole) {
        return fromResultSet(sqlSelect("select * from player_staff_role where staff_role_id = ?", staffRole.id));
    }

    public static @NotNull ArrayList<PlayerStaffRole> fromPlayer(ServerPlayer player) {
        return fromResultSet(sqlSelect("select * from player_staff_role where player_id = ?", player.id));
    }

    public PlayerStaffRole() {}

    public PlayerStaffRole(ServerPlayer player, StaffRole staffRole) {
        this.player = player;
        this.staffRole = staffRole;
    }

    public void save() {
        sqlInsert("insert into player_staff_role (player_id, staff_role_id) values (?, ?)",
                player.id, staffRole.id);
    }

    private static @NotNull ArrayList<PlayerStaffRole> fromResultSet(ResultSet rs) {
        try {
            ArrayList<PlayerStaffRole> list = new ArrayList<>();
            while (rs.next()) {
                PlayerStaffRole psr = new PlayerStaffRole();
                psr.player = ServerPlayer.fromId(rs.getInt("player_id"));
                psr.staffRole = StaffRole.fromId(rs.getInt("staff_role_id"));
                list.add(psr);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


