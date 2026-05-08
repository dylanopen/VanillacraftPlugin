package com.lapisdev.vanillacraft.database;

import java.sql.Connection;
import java.sql.SQLException;

import static com.lapisdev.vanillacraft.VanillacraftPlugin.plugin;
import static com.lapisdev.vanillacraft.task.RunTask.async;

public class Database {
    public static Connection conn;

    public static void init() {
        try {
            plugin().getDataFolder().mkdirs();
            Class.forName("org.sqlite.JDBC");
            conn = java.sql.DriverManager.getConnection("jdbc:sqlite:" + plugin().getDataFolder().toString() + "/vanillacraft.db");
            DatabaseTables.createTables();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        try {
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTable(String table) {
        async((task) -> {
            try {
                conn.createStatement().execute("CREATE TABLE IF NOT EXISTS " + table);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}