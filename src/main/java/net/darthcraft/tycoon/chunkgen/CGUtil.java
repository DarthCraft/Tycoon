package net.darthcraft.tycoon.chunkgen;

class CGUtil {

    static final int CHUNK_SECTION_SIZE = 4096;

    static final int DIRT_MAX_HEIGHT = 63;

    static final int PLOT_SIZE = 32;
    static final int PATH_WIDTH = 5;
    static final int SLAB_LEVEL = DIRT_MAX_HEIGHT + 1;
    static final int SLAB_WIDTH = 2;
    static final int GRID_CELL_SIZE = PLOT_SIZE + PATH_WIDTH + SLAB_WIDTH;

    static final int SLAB_MOD_LOC_1 = PATH_WIDTH;
    static final int SLAB_MOD_LOC_2 = GRID_CELL_SIZE - 1;


    static final int modGridSize(int num) {
        return ((num % GRID_CELL_SIZE) + GRID_CELL_SIZE) % GRID_CELL_SIZE;
    }

}
