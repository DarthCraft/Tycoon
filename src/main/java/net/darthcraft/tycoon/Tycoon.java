package net.darthcraft.tycoon;

import net.darthcraft.tycoon.chunkgen.TycoonChunkGen;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Tycoon extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // DEBUG THINGS
        File tycoonWorld = new File(getServer().getWorldContainer(), "tycoon");
        if (tycoonWorld.exists()) {
            recursiveDelete(tycoonWorld);
        }
        WorldCreator creator = new WorldCreator("tycoon");
        creator.generator(new TycoonChunkGen());
        tycoon = creator.createWorld();
        tycoon.setSpawnLocation(0, 64, 0);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {

    }




    // DEBUG
    private World tycoon;

    public void recursiveDelete(File file) {
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                recursiveDelete(f);
            }
            f.delete();

        }
        file.delete();
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        getServer().getScheduler().runTask(this, new Runnable() {
            @Override
            public void run() {
                event.getPlayer().teleport(tycoon.getSpawnLocation());
            }
        });
    }
}
