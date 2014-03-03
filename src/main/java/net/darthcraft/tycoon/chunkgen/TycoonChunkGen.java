package net.darthcraft.tycoon.chunkgen;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static net.darthcraft.tycoon.PlotUtil.*;

public class TycoonChunkGen extends ChunkGenerator {

    @Override
    public byte[][] generateBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
        int numSections = world.getMaxHeight() / 16;
        byte[][] ret = new byte[numSections][];
        ret[0] = new byte[CHUNK_SECTION_SIZE];
        for (int xx = 0; xx < 16; xx++) {
            for (int zz = 0; zz < 16; zz++) {
                setBlockFast(ret, xx, 0, zz, (byte) 7);
                biomes.setBiome(xx, zz, Biome.PLAINS);
            }
        }

        for (int i = DIRT_MAX_HEIGHT >> 4; i > 0; i--) {
            ret[i] = new byte[CHUNK_SECTION_SIZE];
        }

        for (int yy = 1; yy <= DIRT_MAX_HEIGHT; yy++) {
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
        if (ret[SLAB_LEVEL >> 4] == null) {
            ret[SLAB_LEVEL >> 4] = new byte[CHUNK_SECTION_SIZE];
        }
        for (int xx = 0; xx < 16; xx++) {
            int nX = (x << 4) + xx;
            int mX = modGridSize(nX);
            for (int zz = 0; zz < 16; zz++) {
                int nZ = (z << 4) + zz;
                int mZ = modGridSize(nZ);
                if (mX == SIGN_X_POS && mZ == SIGN_Z_POS) {
                    setBlockFast(ret, xx, SLAB_LEVEL, zz, (byte) 68);
                }
                if (mX < PATH_WIDTH || mZ < PATH_WIDTH) {
                    continue;
                }

                if (mX == SLAB_MOD_LOC_1 || mX == SLAB_MOD_LOC_2 || mZ == SLAB_MOD_LOC_1 || mZ == SLAB_MOD_LOC_2) {
                    setBlockFast(ret, xx, SLAB_LEVEL, zz, (byte) 44);
                }
            }
        }

        return ret;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        ArrayList<BlockPopulator> pops = new ArrayList<BlockPopulator>();
        pops.add(new SignBlockPop());
        pops.add(new PathBlockPop());
        return pops;
    }

    private void setBlockFast(byte[][] data, int x, int y, int z, byte type) {
        data[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = type;
    }
}
