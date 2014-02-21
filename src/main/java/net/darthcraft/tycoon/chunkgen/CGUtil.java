package net.darthcraft.tycoon.chunkgen;

import net.darthcraft.tycoon.Util;

public class CGUtil {

    static final int CHUNK_SECTION_SIZE = 4096;

    static final int DIRT_MAX_HEIGHT = 63;

    static final int PLOT_SIZE = 32;
    static final int PATH_WIDTH = 5;
    static final int SLAB_LEVEL = DIRT_MAX_HEIGHT + 1;
    static final int SLAB_WIDTH = 2;
    public static final int GRID_CELL_SIZE = PLOT_SIZE + PATH_WIDTH + SLAB_WIDTH;

    static final int SLAB_MOD_LOC_1 = PATH_WIDTH;
    static final int SLAB_MOD_LOC_2 = GRID_CELL_SIZE - 1;

    static final int SIGN_X_POS = PATH_WIDTH + 1;
    static final int SIGN_Z_POS = PATH_WIDTH - 1;

    static int modGridSize(int num) {
        return Util.posNegMod(num, GRID_CELL_SIZE);
    }
}
