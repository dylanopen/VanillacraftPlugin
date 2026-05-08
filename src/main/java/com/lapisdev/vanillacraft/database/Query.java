package com.lapisdev.vanillacraft.database;

import org.codehaus.plexus.util.StringUtils;

import java.sql.ResultSet;

public class Query {
    /// `query` MUST be a string literal, or SQL injection may occur (as it is not escaped).
    public static ResultSet sqlSelect(String query, Object... params) {
        try {
            var stmt = Database.conn.prepareStatement(query);
            for (int i = 0; i < Math.min(params.length, StringUtils.countMatches(query, "?")); i++) {
                stmt.setObject(i + 1, params[i]);
            }
            return stmt.executeQuery();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int sqlUpdate(String query, Object... params) {
        try {
            var stmt = Database.conn.prepareStatement(query);
            for (int i = 0; i < Math.min(params.length, StringUtils.countMatches(query, "?")); i++) {
                stmt.setObject(i + 1, params[i]);
            }
            return stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // The returned integer represents the newly generated key (id) int.
    public static int sqlInsert(String query, Object... params) {
        try {
            var stmt = Database.conn.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < Math.min(params.length, StringUtils.countMatches(query, "?")); i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();
            var rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new RuntimeException("Failed to retrieve generated key");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void sqlDelete(String query, Object... params) {
        try {
            var stmt = Database.conn.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Returns `0` if the database was updated (the value existed before under the `updateQuery`)
    // Returns a positive int if the database was inserted into (the value did not exist before). This integer represents the newly generated key (id) int.
    public static int sqlUpdateOrInsert(String updateQuery, String insertQuery, Object... params) {
        try {
            var stmt = Database.conn.prepareStatement(updateQuery);
            for (int i = 0; i < Math.min(params.length, StringUtils.countMatches(updateQuery, "?")); i++) {
                stmt.setObject(i + 1, params[i]);
            }
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return sqlUpdate(insertQuery, params);
            }
            return 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
