package net.darthcraft.tycoon.chunkgen;

import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TycoonChunkGen extends ChunkGenerator {

    private static final int CHUNK_SECTION_SIZE = 4096;

    private static final int DIRT_MAX_HEIGHT = 64;

    // TODO: Add in plot generation surrounded by stone bricks

    @Override
    public byte[][] generateBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
        int numSections = world.getMaxHeight() / 16;
        byte[][] ret = new byte[numSections][];
        ret[0] = new byte[CHUNK_SECTION_SIZE];
        for (int xx = 0; xx < 16; xx++) {
            for (int zz = 0; zz < 16; zz++) {
                setBlockFast(ret, xx, 0, zz, (byte) 7);
            }
        }
        for (int i = DIRT_MAX_HEIGHT >> 4; i > 0; i--) {
            ret[i] = new byte[CHUNK_SECTION_SIZE];
        }
        for (int yy = 0; yy <= DIRT_MAX_HEIGHT; yy++) {
            for (int xx = 0; xx < 16; xx++) {
                for (int zz = 0; zz < 16; zz++) {
                    if (yy == DIRT_MAX_HEIGHT) {
                        setBlockFast(ret, xx, yy, zz, (byte) 2);
                    } else {
                        setBlockFast(ret, xx, yy, zz, (byte) 3);
                    }
                }
            }
        }

        return ret;
    }

    private void setBlockFast(byte[][] data, int x, int y, int z, byte type) {
        data[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = type;
    }
}
