package net.darthcraft.tycoon.chunkgen;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

import static net.darthcraft.tycoon.chunkgen.CGUtil.*;

public class PathBlockPop extends BlockPopulator {

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
                if (mX < PATH_WIDTH || mZ < PATH_WIDTH) {
                    chunk.getBlock(x, DIRT_MAX_HEIGHT, z).setTypeId(1, false);
                }
            }
        }
    }
}
