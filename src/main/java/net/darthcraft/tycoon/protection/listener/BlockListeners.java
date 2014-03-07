package net.darthcraft.tycoon.protection.listener;

import net.darthcraft.tycoon.PlotCoords;
import net.darthcraft.tycoon.PlotUtil;
import net.darthcraft.tycoon.Tycoon;
import net.darthcraft.tycoon.plot.PlotInformation;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.StructureGrowEvent;

import java.util.Iterator;

public class BlockListeners implements Listener {

    private final Tycoon plugin;

    public BlockListeners(Tycoon plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.getPlayer().getWorld().equals(plugin.getTycoonWorld())) {
            return;
        }
        int x = event.getBlock().getX();
        int z = event.getBlock().getZ();
        int mX = PlotUtil.modGridSize(x);
        int mZ = PlotUtil.modGridSize(z);
        boolean isInPath = PlotUtil.isInPath(mX, mZ);
        if (isInPath) {
            event.setCancelled(true);
        }
        PlotCoords coords = PlotUtil.worldCoordsToPlotCoords(x, z);
        PlotInformation info = plugin.getPlotManager().getPlotInformation(coords);
        if (info.isGlobalOwned() && info.isGlobalProtected()) {
            event.setCancelled(true);
        } else if (info.isAllowed(event.getPlayer().getName())) {
            event.setCancelled(true);
        } else if (!info.getOwner().equalsIgnoreCase(event.getPlayer().getName())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!event.getPlayer().getWorld().equals(plugin.getTycoonWorld())) {
            return;
        }
        int x = event.getBlock().getX();
        int z = event.getBlock().getZ();
        int mX = PlotUtil.modGridSize(x);
        int mZ = PlotUtil.modGridSize(z);
        boolean isInPath = PlotUtil.isInPath(mX, mZ);
        if (isInPath) {
            event.setCancelled(true);
        }
        PlotCoords coords = PlotUtil.worldCoordsToPlotCoords(x, z);
        PlotInformation info = plugin.getPlotManager().getPlotInformation(coords);
        if (info.isGlobalOwned() && info.isGlobalProtected()) {
            event.setCancelled(true);
        } else if (info.isAllowed(event.getPlayer().getName())) {
            event.setCancelled(true);
        } else if (!info.getOwner().equalsIgnoreCase(event.getPlayer().getName())) {
            event.setCancelled(true);
        }
    }

    // McMMO Beserker mainly
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockDamage(BlockDamageEvent event) {
        if (!event.getPlayer().getWorld().equals(plugin.getTycoonWorld())) {
            return;
        }
        int x = event.getBlock().getX();
        int z = event.getBlock().getZ();
        int mX = PlotUtil.modGridSize(x);
        int mZ = PlotUtil.modGridSize(z);
        boolean isInPath = PlotUtil.isInPath(mX, mZ);
        if (isInPath) {
            event.setCancelled(true);
        }
        PlotCoords coords = PlotUtil.worldCoordsToPlotCoords(x, z);
        PlotInformation info = plugin.getPlotManager().getPlotInformation(coords);
        if (info.isGlobalOwned() && info.isGlobalProtected()) {
            event.setCancelled(true);
        } else if (info.isAllowed(event.getPlayer().getName())) {
            event.setCancelled(true);
        } else if (!info.getOwner().equalsIgnoreCase(event.getPlayer().getName())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockFromToEvent(BlockFromToEvent event) {
        if (!event.getBlock().getWorld().equals(plugin.getTycoonWorld())) {
            return;
        }
        int oX = event.getBlock().getX();
        int oZ = event.getBlock().getZ();
        int mOX = PlotUtil.modGridSize(oX);
        int mOZ = PlotUtil.modGridSize(oZ);
        int nX = event.getToBlock().getX();
        int nZ = event.getToBlock().getZ();
        int mNX = PlotUtil.modGridSize(nX);
        int mNZ = PlotUtil.modGridSize(nZ);
        boolean isNInPath = PlotUtil.isInPath(mNX, mNZ);
        boolean isOInPath = PlotUtil.isInPath(mOX, mOZ);
        if (!isOInPath && isNInPath) {
            event.setCancelled(true);
        } else {
            PlotCoords oCoords = PlotUtil.worldCoordsToPlotCoords(oX, oZ);
            PlotCoords nCoords = PlotUtil.worldCoordsToPlotCoords(nX, nZ);
            if (!oCoords.equals(nCoords)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockFormEvent(BlockFormEvent event) {
        if (!event.getBlock().getWorld().equals(plugin.getTycoonWorld())) {
            return;
        }
        int oX = event.getBlock().getX();
        int oZ = event.getBlock().getZ();
        int mOX = PlotUtil.modGridSize(oX);
        int mOZ = PlotUtil.modGridSize(oZ);
        int nX = event.getNewState().getX();
        int nZ = event.getNewState().getZ();
        int mNX = PlotUtil.modGridSize(nX);
        int mNZ = PlotUtil.modGridSize(nZ);
        boolean isNInPath = PlotUtil.isInPath(mNX, mNZ);
        boolean isOInPath = PlotUtil.isInPath(mOX, mOZ);
        if (!isOInPath && isNInPath) {
            event.setCancelled(true);
        } else {
            PlotCoords oCoords = PlotUtil.worldCoordsToPlotCoords(oX, oZ);
            PlotCoords nCoords = PlotUtil.worldCoordsToPlotCoords(nX, nZ);
            if (!oCoords.equals(nCoords)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPistonExtendEvent(BlockPistonExtendEvent event) {
        if (!event.getBlock().getWorld().equals(plugin.getTycoonWorld())) {
            return;
        }
        int oX = event.getBlock().getX();
        int oZ = event.getBlock().getZ();
        int mOX = PlotUtil.modGridSize(oX);
        int mOZ = PlotUtil.modGridSize(oZ);
        boolean isOInPath = PlotUtil.isInPath(mOX, mOZ);
        for (Block b : event.getBlocks()) {
            int nX = b.getX();
            int nZ = b.getZ();
            int mNX = PlotUtil.modGridSize(nX);
            int mNZ = PlotUtil.modGridSize(nZ);
            if (PlotUtil.isInPath(mNX, mNZ) == isOInPath) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onStructureGrowEvent(StructureGrowEvent event) {
        if (!event.getLocation().getWorld().equals(plugin.getTycoonWorld())) {
            return;
        }
        int x = event.getLocation().getBlockX();
        int z = event.getLocation().getBlockZ();
        int mX = PlotUtil.modGridSize(x);
        int mZ = PlotUtil.modGridSize(z);
        boolean isInPlot = PlotUtil.isInPlot(mX, mZ);
        PlotCoords coords = PlotUtil.worldCoordsToPlotCoords(x, z);
        Iterator<BlockState> it = event.getBlocks().iterator();
        while (it.hasNext()) {
            BlockState state = it.next();
            int nX = state.getX();
            int nZ = state.getZ();
            int mNX = PlotUtil.modGridSize(nX);
            int mNZ = PlotUtil.modGridSize(nZ);
            PlotCoords nCoords = PlotUtil.worldCoordsToPlotCoords(nX, nZ);
            if (PlotUtil.isInPlot(mNX, mNZ) && !isInPlot) {
                it.remove();
            } else if (isInPlot && !nCoords.equals(coords)) {
                it.remove();
            }
        }
    }

    @EventHandler
    public void onPortalCreate(PortalCreateEvent event) {
        if (event.getWorld().equals(plugin.getTycoonWorld())) {
            event.setCancelled(true);
        }
    }
}
