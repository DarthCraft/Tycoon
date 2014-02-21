package net.darthcraft.tycoon.chunkgen;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

import static net.darthcraft.tycoon.chunkgen.CGUtil.*;

public class PathBlockPop extends BlockPopulator {

    private static final int BIRCH_STAIRS = 135;
    private static final int OAK_STAIRS = 53;
    private static final int WOODEN_SLAB = 126;

    private static final byte FACE_SOUTH = 3;
    private static final byte FACE_NORTH = 2;
    private static final byte FACE_WEST = 0;
    private static final byte FACE_EAST = 1;

    private static final byte OAK_SLAB = 0;
    private static final byte BIRCH_SLAB = 2;

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        int chunkXOff = chunk.getX() << 4;
        int chunkZOff = chunk.getZ() << 4;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int nX = chunkXOff + x;
                int nZ = chunkZOff + z;
                int mX = modGridSize(nX);
                int mZ = modGridSize(nZ);
                if (mX >= PATH_WIDTH && mZ >= PATH_WIDTH) {
                    continue;
                }
                Block b = chunk.getBlock(x, DIRT_MAX_HEIGHT, z);
                if (mX == 0) {
                    if (mZ == 0) {
                        b.setTypeIdAndData(BIRCH_STAIRS, FACE_SOUTH, false);
                    } else if (mZ < PATH_WIDTH - 1) {
                        b.setTypeIdAndData(WOODEN_SLAB, OAK_SLAB, false);
                    } else if (mZ <= PATH_WIDTH) {
                        b.setTypeIdAndData(BIRCH_STAIRS, FACE_EAST, false);
                    } else if (mZ == GRID_CELL_SIZE - 1) {
                        b.setTypeIdAndData(BIRCH_STAIRS, FACE_EAST, false);
                    } else {
                        b.setTypeIdAndData(OAK_STAIRS, FACE_EAST, false);
                    }
                } else if (mZ == 0) {
                    if (mX < PATH_WIDTH - 1) {
                        b.setTypeIdAndData(WOODEN_SLAB, OAK_SLAB, false);
                    } else if (mX <= PATH_WIDTH) {
                        b.setTypeIdAndData(BIRCH_STAIRS, FACE_SOUTH, false);
                    } else if (mX == GRID_CELL_SIZE - 1) {
                        b.setTypeIdAndData(BIRCH_STAIRS, FACE_SOUTH, false);
                    } else {
                        b.setTypeIdAndData(OAK_STAIRS, FACE_SOUTH, false);
                    }
                } else if (mZ == PATH_WIDTH - 1) {
                    if (mX < PATH_WIDTH - 1) {
                        b.setTypeIdAndData(WOODEN_SLAB, OAK_SLAB, false);
                    } else if (mX <= PATH_WIDTH) {
                        b.setTypeIdAndData(BIRCH_STAIRS, FACE_NORTH, false);
                    } else if (mX == GRID_CELL_SIZE - 1) {
                        b.setTypeIdAndData(BIRCH_STAIRS, FACE_NORTH, false);
                    } else {
                        b.setTypeIdAndData(OAK_STAIRS, FACE_NORTH, false);
                    }
                } else if (mX == PATH_WIDTH - 1) {
                    if (mZ < PATH_WIDTH - 1) {
                        b.setTypeIdAndData(WOODEN_SLAB, OAK_SLAB, false);
                    } else if (mZ <= PATH_WIDTH) {
                        b.setTypeIdAndData(BIRCH_STAIRS, FACE_WEST, false);
                    } else if (mZ == GRID_CELL_SIZE - 1) {
                        b.setTypeIdAndData(BIRCH_STAIRS, FACE_WEST, false);
                    } else {
                        b.setTypeIdAndData(OAK_STAIRS, FACE_WEST, false);
                    }
                } else if (mX < PATH_WIDTH || mZ < PATH_WIDTH) {
                    if ((mX == 1 || mX == 3) && (mZ == 1 || mZ == 3)) {
                        b.setTypeIdAndData(WOODEN_SLAB, OAK_SLAB, false);
                    } else {
                        b.setTypeIdAndData(WOODEN_SLAB, BIRCH_SLAB, false);
                    }
                }
            }
        }
    }
}
