package net.darthcraft.tycoon.chunkgen;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

public class TycoonBlockPop extends BlockPopulator {

    private static final int CHUNK_SIZE = 16;
    private static final int BEDROCK_LEVEL = 0;
    private static final int GRASS_HEIGHT = 63;

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        int startX = chunk.getX() << 4;
        int startY = chunk.getZ() << 4;
        int endX = startX + 16;
        int endY = startY + 16;
        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {

            }
        }
    }
}
