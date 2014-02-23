package net.darthcraft.tycoon;

import net.darthcraft.tycoon.chunkgen.CGUtil;

public class PlotUtil {

    private static final int MAX_OFFSET = 30000000;
    private static final int MAX_PLOT_OFFSET = MAX_OFFSET / CGUtil.GRID_CELL_SIZE;

    public static long worldCoordsToHash(int x, int z) {
        return plotLocToHash(worldCoordsToPlotCoords(x, z));
    }

    public static PlotCoords worldCoordsToPlotCoords(int x, int z) {
        int nX = (int) Math.floor(((double) x) / CGUtil.GRID_CELL_SIZE);
        int nZ = (int) Math.floor(((double) z) / CGUtil.GRID_CELL_SIZE);
        return new PlotCoords(nX, nZ);
    }

    public static long plotLocToHash(PlotCoords coords) {
        return plotLocToHash(coords.getPlotX(), coords.getPlotZ());
    }

    public static long plotLocToHash(int x, int z) {
        x += MAX_PLOT_OFFSET;
        z += MAX_PLOT_OFFSET;
        return ((long) x << 32) + z - Integer.MIN_VALUE;
    }

    public static PlotCoords plotCoordsFromHash(long hash) {
        int x = (int) (hash >> 32) - MAX_PLOT_OFFSET;
        int z = (int) (hash & 0xFFFFFFFF) + Integer.MIN_VALUE - MAX_PLOT_OFFSET;
        return new PlotCoords(x, z);
    }
}
