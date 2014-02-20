package net.darthcraft.tycoon.chunkgen;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

import static net.darthcraft.tycoon.chunkgen.CGUtil.*;

public class SlabBlockPop extends BlockPopulator {

    private static final int SLAB_TYPE = 44;
    private static final byte SLAB_DATA = 0;

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
                    continue;
                }
                if (mX == SLAB_MOD_LOC_1 || mZ == SLAB_MOD_LOC_1 || mX == SLAB_MOD_LOC_2 || mZ == SLAB_MOD_LOC_2) {
                    chunk.getBlock(x, SLAB_LEVEL, z).setTypeIdAndData(SLAB_TYPE, SLAB_DATA, false);
//                    if (mX == SIGN_X_POS && mZ == SIGN_Z_POS + 1) {
//                        world.getBlockAt(nX, SLAB_LEVEL, nZ - 1).setTypeIdAndData(Material.WALL_SIGN.getId(), (byte) 2, false);
//                        Sign s = (Sign) world.getBlockAt(x, SLAB_LEVEL, z).getState();
//                        s.setLine(0, "Plot Available");
//                        s.setLine(1, "Right Click to");
//                        s.setLine(2, "Buy");
//                        s.update(false, false);
//                    }
                }
            }
        }
    }
}
