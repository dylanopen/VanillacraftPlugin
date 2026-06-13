package com.lapisdev.vanillacraft.market;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.lapisdev.vanillacraft.database.Query.sqlInsert;
import static com.lapisdev.vanillacraft.database.Query.sqlSelect;
import static com.lapisdev.vanillacraft.database.Query.sqlUpdate;

public class ShopItem {
    public int id;
    public ServerPlayer owner;
    public Material material;
    public int price;
    public int quantity;
    public int stock;
    public Location location;
    public Location signLocation;

    public ShopItem(ServerPlayer owner, Material material, int price, int quantity, Location location, Location signLocation) {
        this.owner = owner;
        this.material = material;
        this.price = price;
        this.quantity = quantity;
        this.location = location;
        this.signLocation = signLocation;
        this.stock = 0;
    }

    public ShopItem() {}

    public void save() {
        if (id == 0) {
            id = sqlInsert("insert into shop_item (player_id, shop_item_material, shop_item_price, shop_item_quantity, shop_item_stock, shop_item_world, shop_item_x, shop_item_y, shop_item_z, sign_x, sign_y, sign_z) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    owner.id, material.name(), price, quantity, stock, location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), signLocation.getBlockX(), signLocation.getBlockY(), signLocation.getBlockZ());
        } else {
            sqlUpdate("update shop_item set shop_item_price = ?, shop_item_quantity = ?, shop_item_stock = ?, shop_item_material = ?, sign_x = ?, sign_y = ?, sign_z = ? where shop_item_id = ?",
                    price, quantity, stock, material.name(), signLocation.getBlockX(), signLocation.getBlockY(), signLocation.getBlockZ(), id);
        }
    }

    public void delete() {
        com.lapisdev.vanillacraft.database.Query.sqlDelete("delete from shop_item where shop_item_id = ?", id);
    }

    public static ArrayList<ShopItem> getAll() {
        return fromResultSet(sqlSelect("select * from shop_item"));
    }

    public static ShopItem fromLocation(Location loc) {
        ArrayList<ShopItem> items = fromResultSet(sqlSelect("select * from shop_item where shop_item_world = ? and shop_item_x = ? and shop_item_y = ? and shop_item_z = ?",
                loc.getWorld().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
        return items.isEmpty() ? null : items.get(0);
    }

    public static ShopItem fromSignLocation(Location loc) {
        ArrayList<ShopItem> items = fromResultSet(sqlSelect("select * from shop_item where shop_item_world = ? and sign_x = ? and sign_y = ? and sign_z = ?",
                loc.getWorld().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
        return items.isEmpty() ? null : items.get(0);
    }

    private static @NotNull ArrayList<ShopItem> fromResultSet(ResultSet rs) {
        try {
            ArrayList<ShopItem> items = new ArrayList<>();
            while (rs.next()) {
                ShopItem item = new ShopItem();
                item.id = rs.getInt("shop_item_id");
                item.owner = ServerPlayer.fromId(rs.getInt("player_id"));
                item.material = Material.valueOf(rs.getString("shop_item_material"));
                item.price = rs.getInt("shop_item_price");
                item.quantity = rs.getInt("shop_item_quantity");
                item.stock = rs.getInt("shop_item_stock");
                item.location = new Location(
                        Bukkit.getWorld(rs.getString("shop_item_world")),
                        rs.getInt("shop_item_x"),
                        rs.getInt("shop_item_y"),
                        rs.getInt("shop_item_z")
                );
                item.signLocation = new Location(
                        item.location.getWorld(),
                        rs.getInt("sign_x"),
                        rs.getInt("sign_y"),
                        rs.getInt("sign_z")
                );
                items.add(item);
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
