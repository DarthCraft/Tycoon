package net.darthcraft.tycoon;

public class PlotUtil {

    public static final int CHUNK_SECTION_SIZE = 4096;

    public static final int DIRT_MAX_HEIGHT = 63;

    static final int PLOT_SIZE = 32;
    public static final int PATH_WIDTH = 5;
    public static final int SLAB_LEVEL = DIRT_MAX_HEIGHT + 1;
    static final int SLAB_WIDTH = 2;
    public static final int GRID_CELL_SIZE = PLOT_SIZE + PATH_WIDTH + SLAB_WIDTH;

    public static final int SLAB_MOD_LOC_1 = PATH_WIDTH;
    public static final int SLAB_MOD_LOC_2 = GRID_CELL_SIZE - 1;

    public static final int SIGN_X_POS = PATH_WIDTH + 1;
    public static final int SIGN_Z_POS = PATH_WIDTH - 1;

    private static final int MAX_OFFSET = 30000000;
    public static final int MAX_PLOT_OFFSET = (MAX_OFFSET / GRID_CELL_SIZE) + 1;

    public static int modGridSize(int num) {
        return Util.posNegMod(num, GRID_CELL_SIZE);
    }

    public static long worldCoordsToHash(int x, int z) {
        return plotLocToHash(worldCoordsToPlotCoords(x, z));
    }

    public static PlotCoords worldCoordsToPlotCoords(int x, int z) {
        int nX = (int) Math.floor(((double) x) / GRID_CELL_SIZE);
        int nZ = (int) Math.floor(((double) z) / GRID_CELL_SIZE);
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

    public static boolean isInPlot(int modX, int modZ) {
        return (modX > SLAB_MOD_LOC_1 && modX < SLAB_MOD_LOC_2) && (modZ > SLAB_MOD_LOC_1 && modZ < SLAB_MOD_LOC_2);
    }

    public static boolean isInPath(int modX, int modZ) {
        return modX <= SLAB_MOD_LOC_1 || modZ <= SLAB_MOD_LOC_1 || modX == SLAB_MOD_LOC_2 || modZ == SLAB_MOD_LOC_2;
    }
}
