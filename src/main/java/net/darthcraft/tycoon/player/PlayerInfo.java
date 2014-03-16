package net.darthcraft.tycoon.player;

import net.darthcraft.tycoon.PlotCoords;
import net.darthcraft.tycoon.PlotUtil;
import net.darthcraft.tycoon.Tycoon;
import net.darthcraft.tycoon.plot.PlotInformation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;

public class PlayerInfo {

    private final Tycoon plugin;
    private final String player;

    private boolean offline;

    // Caching things
    private Player cachedPlayer;
    private int previousX;
    private int previousZ;
    private PlotInformation cachedPlotInfo;
    private int previousModX;
    private int previousModZ;
    private boolean isInPlot;


    public PlayerInfo(Tycoon plugin, String player) {
        this.plugin = plugin;
        this.player = player;
    }

    public void update() {
        if (offline) {
            return;
        }
        if (!cachedPlayer.getWorld().equals(plugin.getTycoonWorld())) {
            cachedPlotInfo = null;
            return;
        }
        Location loc = cachedPlayer.getLocation();
        if (loc.getBlockX() == previousX && loc.getBlockZ() == previousZ && cachedPlotInfo != null) {
            return;
        }
        previousX = loc.getBlockX();
        previousZ = loc.getBlockZ();
        previousModX = PlotUtil.modGridSize(previousX);
        previousModZ = PlotUtil.modGridSize(previousZ);
        isInPlot = PlotUtil.isInPlot(previousModX, previousModZ);
        int plotX = PlotUtil.worldCoordToPlotCoord(previousX);
        int plotZ = PlotUtil.worldCoordToPlotCoord(previousZ);
        long hash = PlotUtil.plotLocToHash(plotX, plotZ);
        cachedPlotInfo = plugin.getPlotManager().getPlotInformation(hash);
    }

    public void update(PlayerMoveEvent event) {
        int nX = event.getTo().getBlockX();
        int nY = event.getTo().getBlockY();
        int nZ = event.getTo().getBlockZ();
        int mNX = PlotUtil.modGridSize(nX);
        int mNZ = PlotUtil.modGridSize(nZ);
        boolean toInPath = PlotUtil.isInPath(mNX, mNZ);
        if (!isInPlot() && toInPath) {
            // stayed in path
        } else if (!isInPlot()) {
            // going into plot
            PlotCoords to = PlotUtil.worldCoordsToPlotCoords(nX, nZ);
            PlotInformation toInfo = plugin.getPlotManager().getPlotInformation(to);
            playerPathLeave(event);
            playerPlotEnter(event, toInfo);
        } else if (toInPath) {
            // going into path
            PlotInformation fromInfo = getCachedPlotInfo();
            playerPlotLeave(event, fromInfo);
            playerPathEnter(event);
        } else {
            PlotCoords to = PlotUtil.worldCoordsToPlotCoords(nX, nZ);
            if (to.equals(getCachedPlotInfo().getCoords())) {
                // stayed in plot
            } else {
                PlotInformation toInfo = plugin.getPlotManager().getPlotInformation(to);
                PlotInformation fromInfo = getCachedPlotInfo();
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
            //event.setCancelled(true);
            event.setTo(event.getFrom());
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

    public void onLogout() {
        this.cachedPlayer = null;
        this.cachedPlotInfo = null;
        this.offline = true;
    }

    public void onLogin() {
        this.cachedPlayer = Bukkit.getPlayerExact(player);
        this.offline = false;
    }

    public boolean isInPlot() {
        return isInPlot;
    }

    public PlotInformation getCachedPlotInfo() {
        return cachedPlotInfo;
    }
}
