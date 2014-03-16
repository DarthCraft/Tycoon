package net.darthcraft.tycoon.protection.listener;

import net.darthcraft.tycoon.Tycoon;
import net.darthcraft.tycoon.player.PlayerInfo;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MovementListener implements Listener {

    private final Tycoon plugin;

    public MovementListener(Tycoon plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!event.getFrom().getWorld().equals(plugin.getTycoonWorld()) && !event.getTo().getWorld().equals(plugin.getTycoonWorld())) {
            return;
        }
        if (blockNotChanged(event.getFrom(), event.getTo())) {
            // Anything requiring camera moving goes here
            return;
        }
        PlayerInfo info = plugin.getPlayerManager().getPlayerInfo(event.getPlayer());
        info.update(event);
    }

    private boolean blockNotChanged(Location from, Location to) {
        return from.getWorld().equals(to.getWorld()) && from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ();
    }
}
