package com.lapisdev.vanillacraft.database;

import com.lapisdev.vanillacraft.task.RunTask;

import static com.lapisdev.vanillacraft.database.Database.createTable;

public class DatabaseTables {
    public static void createTables() {
        RunTask.async((task) -> {
            createTable("player (player_id INTEGER PRIMARY KEY AUTOINCREMENT, discord_uuid TEXT, minecraft_uuid TEXT, unique(discord_uuid, minecraft_uuid))");
            createTable("region (region_id INTEGER PRIMARY KEY AUTOINCREMENT, region_name TEXT, spawn_x DOUBLE, spawn_y DOUBLE, spawn_z DOUBLE, leader_id INTEGER)");
            createTable("player_region (player_id INTEGER PRIMARY KEY, region_id INTEGER)");
            createTable("region_poi (region_poi_id INTEGER PRIMARY KEY, region_id INTEGER, region_poi_x INTEGER, region_poi_y INTEGER, region_poi_z INTEGER, region_poi_name STRING)");
            createTable("team (team_id INTEGER PRIMARY KEY AUTOINCREMENT, team_name TEXT, team_suffix TEXT, team_leader INTEGER)");
            createTable("player_team (player_id INTEGER PRIMARY KEY, team_id INTEGER)");
            createTable("staff_role (staff_role_id INTEGER PRIMARY KEY AUTOINCREMENT, staff_role_name TEXT, staff_role_luckperms_group TEXT)");
            createTable("player_staff_role (player_id INTEGER, staff_role_id INTEGER)");
            createTable("team_invite (team_invite_id INTEGER PRIMARY KEY AUTOINCREMENT, player_id INTEGER, team_id INTEGER)");

            createTable("player_login (player_id INTEGER, login_time INTEGER, login_world STRING, login_x DOUBLE, login_y DOUBLE, login_z DOUBLE)");
            createTable("ban (ban_id INTEGER PRIMARY KEY AUTOINCREMENT, player_id INTEGER, ban_reason TEXT, ban_end_time LONG)");

            createTable("vote (vote_id INTEGER PRIMARY KEY AUTOINCREMENT, voter_player_id INTEGER, region_id INTEGER, candidate_player_id INTEGER)");
            createTable("candidate (candidate_player_id INTEGER, region_id INTEGER)");
        });
    }
}
