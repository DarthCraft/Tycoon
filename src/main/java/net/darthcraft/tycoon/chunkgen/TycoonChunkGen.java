package net.darthcraft.tycoon.chunkgen;

import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.ArrayList;
import java.util.List;

public class TycoonChunkGen extends ChunkGenerator {



    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        List<BlockPopulator> pop = new ArrayList<BlockPopulator>(1);
        pop.add(new TycoonBlockPop());
        return pop;
    }
}
