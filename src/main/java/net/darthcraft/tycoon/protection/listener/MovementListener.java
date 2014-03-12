package net.darthcraft.tycoon.protection.listener;

import net.darthcraft.tycoon.PlotCoords;
import net.darthcraft.tycoon.PlotUtil;
import net.darthcraft.tycoon.Tycoon;
import net.darthcraft.tycoon.player.PlayerInfo;
import net.darthcraft.tycoon.plot.PlotInformation;
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
        PlayerInfo info = plugin.getPlayerManager().getPlayerInfo(event.getPlayer());
        info.update();
        int nX = event.getTo().getBlockX();
        int nY = event.getTo().getBlockY();
        int nZ = event.getTo().getBlockZ();
        int mNX = PlotUtil.modGridSize(nX);
        int mNZ = PlotUtil.modGridSize(nZ);
        boolean toInPath = PlotUtil.isInPath(mNX, mNZ);
        if (!info.isInPlot() && toInPath) {
            // stayed in path
        } else if (!info.isInPlot()) {
            // going into plot
            PlotCoords to = PlotUtil.worldCoordsToPlotCoords(nX, nZ);
            PlotInformation toInfo = plugin.getPlotManager().getPlotInformation(to);
            playerPathLeave(event);
            playerPlotEnter(event, toInfo);
        } else if (toInPath) {
            // going into path
            PlotInformation fromInfo = info.getCachedPlotInfo();
            playerPlotLeave(event, fromInfo);
            playerPathEnter(event);
        } else {
            PlotCoords to = PlotUtil.worldCoordsToPlotCoords(nX, nZ);
            if (to.equals(info.getCachedPlotInfo().getCoords())) {
                // stayed in plot
            } else {
                PlotInformation toInfo = plugin.getPlotManager().getPlotInformation(to);
                PlotInformation fromInfo = info.getCachedPlotInfo();
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
