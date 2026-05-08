package com.lapisdev.vanillacraft.staff;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.lapisdev.vanillacraft.database.Query.sqlSelect;
import static com.lapisdev.vanillacraft.database.Query.sqlUpdateOrInsert;

public class StaffRole {
    public int id;
    public String name;
    public String luckpermsGroup;

    public static @Nullable StaffRole fromId(int staffRoleId) {
        return fromResultSet(sqlSelect("select * from staff_role where staff_role_id = ?", staffRoleId));
    }

    public static @Nullable StaffRole fromName(String staffRoleName) {
        return fromResultSet(sqlSelect("select * from staff_role where staff_role_name = ?", staffRoleName));
    }

    public static @Nullable StaffRole fromLuckpermsGroup(String luckpermsGroup) {
        return fromResultSet(sqlSelect("select * from staff_role where staff_role_luckperms_group = ?", luckpermsGroup));
    }

    public StaffRole() {}

    public StaffRole(String name, String luckpermsGroup) {
        this.name = name;
        this.luckpermsGroup = luckpermsGroup;
    }

    public void save() {
        sqlUpdateOrInsert("update staff_role set staff_role_name = ?, staff_role_luckperms_group = ? where staff_role_id = ?",
                "insert into staff_role (staff_role_name, staff_role_luckperms_group) values (?, ?)",
                name, luckpermsGroup, id);
    }

    private static @Nullable StaffRole fromResultSet(ResultSet rs) {
        try {
            if (!rs.next()) return null;
            StaffRole staffRole = new StaffRole();
            staffRole.id = rs.getInt("staff_role_id");
            staffRole.name = rs.getString("staff_role_name");
            staffRole.luckpermsGroup = rs.getString("staff_role_luckperms_group");
            return staffRole;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
