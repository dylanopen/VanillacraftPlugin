package com.lapisdev.vanillacraft.market;

import com.lapisdev.vanillacraft.player.ServerPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MarketListener implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        Block signBlock = event.getBlock();
        
        // Check if sign is attached to a container
        Block attachedBlock;
        if (signBlock.getBlockData() instanceof WallSign wallSign) {
            attachedBlock = signBlock.getRelative(wallSign.getFacing().getOppositeFace());
        } else {
            attachedBlock = signBlock.getRelative(0, -1, 0);
        }

        if (!(attachedBlock.getState() instanceof Container container)) return;

        String[] lines = new String[4];
        for (int i = 0; i < 4; i++) {
            lines[i] = PlainTextComponentSerializer.plainText().serialize(event.line(i) != null ? event.line(i) : Component.empty());
        }

        try {
            int price = Integer.parseInt(lines[0]);
            int quantity = Integer.parseInt(lines[1]);

            // Get the first item in the container to determine the material
            ItemStack firstItem = null;
            for (ItemStack item : container.getInventory().getContents()) {
                if (item != null && !item.getType().isAir()) {
                    firstItem = item;
                    break;
                }
            }

            if (firstItem == null) {
                player.sendMessage(Component.text("The container must have at least one item to set as the shop material.", NamedTextColor.RED));
                return;
            }

            Material material = firstItem.getType();
            ServerPlayer serverPlayer = ServerPlayer.fromMinecraftUuid(player.getUniqueId());
            
            // Check if there's already a shop at this sign location (for updates)
            ShopItem shopItem = ShopItem.fromSignLocation(signBlock.getLocation());
            if (shopItem == null) {
                // If not by sign, check by container (maybe it's a new sign for an existing shop, or a completely new shop)
                shopItem = ShopItem.fromLocation(attachedBlock.getLocation());
            }

            if (shopItem == null) {
                shopItem = new ShopItem(serverPlayer, material, price, quantity, attachedBlock.getLocation(), signBlock.getLocation());
            } else {
                shopItem.material = material;
                shopItem.price = price;
                shopItem.quantity = quantity;
                shopItem.signLocation = signBlock.getLocation();
            }
            
            updateStock(shopItem, container.getInventory());
            shopItem.save();

            // Format the sign
            String formattedMaterial = MarketCmd.formatMaterial(material);
            event.line(0, Component.text("Buy ", NamedTextColor.WHITE)
                    .append(Component.text(quantity, NamedTextColor.GOLD)));
            event.line(1, Component.text(formattedMaterial, NamedTextColor.YELLOW));
            event.line(2, Component.text("for ", NamedTextColor.WHITE)
                    .append(Component.text(price, NamedTextColor.AQUA))
                    .append(Component.text(" diamonds", NamedTextColor.WHITE)));
            
            player.sendMessage(Component.text("Shop created for " + formattedMaterial + " at " + price + " diamonds for " + quantity + ".", NamedTextColor.GREEN));
        } catch (NumberFormatException ignored) {
            // Not a shop sign or invalid format
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getState() instanceof org.bukkit.block.Sign) {
            ShopItem shopItem = ShopItem.fromSignLocation(block.getLocation());
            if (shopItem != null) {
                Player player = event.getPlayer();
                ServerPlayer serverPlayer = ServerPlayer.fromMinecraftUuid(player.getUniqueId());
                
                if (serverPlayer == null || shopItem.owner.id != serverPlayer.id) {
                    player.sendMessage(Component.text("You do not own this shop!", NamedTextColor.RED));
                    event.setCancelled(true);
                    return;
                }
                
                shopItem.delete();
                player.sendMessage(Component.text("Shop removed.", NamedTextColor.YELLOW));
            }
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        processInventoryUpdate(event.getInventory());
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        processInventoryUpdate(event.getInventory());
    }

    private void processInventoryUpdate(Inventory inv) {
        if (inv instanceof org.bukkit.inventory.DoubleChestInventory doubleInv) {
            // Check for shops on either side of the double chest
            updateStockForInventory(doubleInv.getLeftSide(), inv);
            updateStockForInventory(doubleInv.getRightSide(), inv);
        } else {
            updateStockForInventory(inv, inv);
        }
    }

    private void updateStockForInventory(Inventory sideInv, Inventory fullInv) {
        if (sideInv.getLocation() == null) return;
        ShopItem shopItem = ShopItem.fromLocation(sideInv.getLocation());
        if (shopItem != null) {
            updateStock(shopItem, fullInv);
            shopItem.save();
        }
    }

    private void updateStock(ShopItem shopItem, Inventory inv) {
        int count = 0;
        for (ItemStack item : inv.getContents()) {
            if (item != null && item.getType() == shopItem.material) {
                count += item.getAmount();
            }
        }
        shopItem.stock = count;
    }
}
