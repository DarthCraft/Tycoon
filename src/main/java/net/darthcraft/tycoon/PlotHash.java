package net.darthcraft.tycoon;

import net.darthcraft.tycoon.chunkgen.CGUtil;

public class PlotHash {

    private static final int MAX_OFFSET = 30000000;
    private static final int MAX_PLOT_OFFSET = MAX_OFFSET / CGUtil.GRID_CELL_SIZE;
    private static final int WORLD_LENGTH = MAX_OFFSET * 2;
    private static final int X_MULT = WORLD_LENGTH / CGUtil.GRID_CELL_SIZE + 1;

//    public static long worldCoordsToHash(int x, int z) {
//
//    }

    public static long plotLocToHash(PlotCoords coords) {
        int x = coords.getPlotX() + MAX_PLOT_OFFSET;
        int z = coords.getPlotZ() + MAX_PLOT_OFFSET;
        return ((long) x << 32) + z - Integer.MIN_VALUE;
    }

    public static PlotCoords plotCoordsFromHash(long hash) {
        int x = (int) (hash >> 32) - MAX_PLOT_OFFSET;
        int z = (int) (hash & 0xFFFFFFFF) + Integer.MIN_VALUE - MAX_PLOT_OFFSET;
        return new PlotCoords(x, z);
    }
}
