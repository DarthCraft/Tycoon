package net.darthcraft.tycoon.protection.listener;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MovementListeners implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (blockNotChanged(event.getFrom(), event.getTo())) {
            // Anything requiring camera moving goes here
            return;
        }
        int nX = event.getTo().getBlockX();
        int nY = event.getTo().getBlockY();
        int nZ = event.getTo().getBlockZ();
        int oX = event.getFrom().getBlockX();
        int oY = event.getFrom().getBlockY();
        int oZ = event.getFrom().getBlockZ();


    }

    private boolean blockNotChanged(Location from, Location to) {
        return from.getWorld().equals(to.getWorld()) && from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ();
    }
}
