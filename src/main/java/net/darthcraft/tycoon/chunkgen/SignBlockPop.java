package net.darthcraft.tycoon.chunkgen;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

import static net.darthcraft.tycoon.chunkgen.CGUtil.*;

public class SignBlockPop extends BlockPopulator {

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
                if (mX == SIGN_X_POS && mZ == SIGN_Z_POS) {
                    chunk.getBlock(x, SLAB_LEVEL, z).setData((byte) 2);
                    Sign s = (Sign) chunk.getBlock(x, SLAB_LEVEL, z).getState();
                    s.setLine(0, "Plot Available");
                    s.setLine(1, "Right Click to");
                    s.setLine(2, "Buy");
                    s.update(false, false);
                }
            }
        }
    }
}
