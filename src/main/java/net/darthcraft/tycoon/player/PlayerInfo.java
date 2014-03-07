package net.darthcraft.tycoon.player;

import net.darthcraft.tycoon.PlotUtil;
import net.darthcraft.tycoon.Tycoon;
import net.darthcraft.tycoon.plot.PlotInformation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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
