package net.darthcraft.tycoon.protection.listener;

import net.darthcraft.tycoon.PlotCoords;
import net.darthcraft.tycoon.PlotUtil;
import net.darthcraft.tycoon.Tycoon;
import net.darthcraft.tycoon.plot.PlotInformation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;

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

        int nX = event.getTo().getBlockX();
        int nY = event.getTo().getBlockY();
        int nZ = event.getTo().getBlockZ();
        int oX = event.getFrom().getBlockX();
        int oY = event.getFrom().getBlockY();
        int oZ = event.getFrom().getBlockZ();
        int mNX = PlotUtil.modGridSize(nX);
        int mNZ = PlotUtil.modGridSize(nZ);
        int mOX = PlotUtil.modGridSize(oX);
        int mOZ = PlotUtil.modGridSize(oZ);
        boolean fromInPath = PlotUtil.isPathCoords(mOX, mOZ);
        boolean toInPath = PlotUtil.isPathCoords(mNX, mNZ);
        if (fromInPath && toInPath) {
            // stayed in path
        } else if (fromInPath) {
            // going into plot
            PlotCoords to = PlotUtil.worldCoordsToPlotCoords(nX, nZ);
            PlotInformation toInfo = plugin.getPlotManager().getPlotInformation(to);
            playerPathLeave(event);
            playerPlotEnter(event, toInfo);
        } else if (toInPath) {
            // going into path
            Bukkit.broadcastMessage("TYCOON: " + event.getPlayer().getName() + " LEFT PLOT");
            PlotCoords from = PlotUtil.worldCoordsToPlotCoords(oX, oZ);
            PlotInformation fromInfo = plugin.getPlotManager().getPlotInformation(from);
            playerPlotLeave(event, fromInfo);
            playerPathEnter(event);
        } else {
            PlotCoords to = PlotUtil.worldCoordsToPlotCoords(nX, nZ);
            PlotCoords from = PlotUtil.worldCoordsToPlotCoords(oX, oZ);
            if (to.equals(from)) {
                // stayed in plot
            } else {
                PlotInformation toInfo = plugin.getPlotManager().getPlotInformation(to);
                PlotInformation fromInfo = plugin.getPlotManager().getPlotInformation(from);
                playerPlotLeave(event, fromInfo);
                playerPlotEnter(event, toInfo);
            }
        }
    }

    private void playerPathEnter(PlayerMoveEvent event) {
        // Add path potion effects
    }

    private void playerPathLeave(PlayerMoveEvent event) {
        // Removing path potion effects

    }

    private void playerPlotEnter(PlayerMoveEvent event, PlotInformation info) {
        if (info.isDenied(event.getPlayer().getName())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "You can not enter this plot!");
            playerPathEnter(event);
        } else {
            event.getPlayer().addPotionEffects(info.getEffects());
            Bukkit.broadcastMessage("TYCOON: " + event.getPlayer().getName() + " PLOT(" + info.getCoords().getPlotX() + "," + info.getCoords().getPlotZ() + ")");
        }
    }

    private void playerPlotLeave(PlayerMoveEvent event, PlotInformation info) {
        for (PotionEffect effect : info.getEffects()) {
            event.getPlayer().removePotionEffect(effect.getType());
        }
    }

    private boolean blockNotChanged(Location from, Location to) {
        return from.getWorld().equals(to.getWorld()) && from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ();
    }
}
