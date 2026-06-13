package com.lapisdev.vanillacraft.database;

import com.lapisdev.vanillacraft.task.RunTask;

import static com.lapisdev.vanillacraft.database.Database.createTable;

public class DatabaseTables {
    public static void createTables() {
        RunTask.async((task) -> {
            createTable("player (player_id INTEGER PRIMARY KEY AUTOINCREMENT, discord_uuid TEXT, minecraft_uuid TEXT, unique(discord_uuid, minecraft_uuid))");
            createTable("team (team_id INTEGER PRIMARY KEY AUTOINCREMENT, team_name TEXT, team_suffix TEXT, team_leader INTEGER)");
            createTable("player_team (player_id INTEGER PRIMARY KEY, team_id INTEGER)");
            createTable("staff_role (staff_role_id INTEGER PRIMARY KEY AUTOINCREMENT, staff_role_name TEXT, staff_role_luckperms_group TEXT, staff_role_discord_role_id TEXT)");
            createTable("player_staff_role (player_id INTEGER, staff_role_id INTEGER)");
            createTable("team_invite (team_invite_id INTEGER PRIMARY KEY AUTOINCREMENT, player_id INTEGER, team_id INTEGER)");
            createTable("shop_item (shop_item_id INTEGER PRIMARY KEY AUTOINCREMENT, player_id INTEGER, shop_item_material TEXT, shop_item_price INTEGER, shop_item_quantity INTEGER, shop_item_stock INTEGER, shop_item_world TEXT, shop_item_x INTEGER, shop_item_y INTEGER, shop_item_z INTEGER, sign_x INTEGER, sign_y INTEGER, sign_z INTEGER)");

            createTable("player_login (player_id INTEGER, login_time INTEGER, login_world STRING, login_x DOUBLE, login_y DOUBLE, login_z DOUBLE)");
            createTable("ban (ban_id INTEGER PRIMARY KEY AUTOINCREMENT, player_id INTEGER, ban_reason TEXT, ban_end_time LONG)");
        });
    }
}
