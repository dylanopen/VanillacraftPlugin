package com.lapisdev.vanillacraft.other;

import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;

import static com.lapisdev.vanillacraft.other.MinecartSpeedCmd.minecartSpeed;

public class PlayerMinecartEvent implements Listener {
    @EventHandler
    public void onMinecartRide(VehicleMoveEvent event){
        if (event.getVehicle() instanceof Minecart){
            Minecart minecart = (Minecart) event.getVehicle();
            if (!minecart.getPassengers().isEmpty() && minecart.getPassengers().get(0) instanceof Player){
                minecart.setMaxSpeed(minecartSpeed);
            }
            else {
                minecart.setMaxSpeed(0.4D);
            }
        }
    }

    @EventHandler
    public void onMinecartExit(VehicleExitEvent event){
        if (event.getVehicle() instanceof Minecart){
            Minecart minecart = (Minecart) event.getVehicle();
            minecart.setMaxSpeed(0.4D);
        }
    }
}
