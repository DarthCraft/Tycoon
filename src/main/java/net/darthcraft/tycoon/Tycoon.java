package net.darthcraft.tycoon;

import net.darthcraft.tycoon.chunkgen.TycoonChunkGen;
import net.darthcraft.tycoon.plot.PlotInformation;
import net.darthcraft.tycoon.plot.PlotManager;
import net.darthcraft.tycoon.protection.listener.MovementListener;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;

public class Tycoon extends JavaPlugin implements Listener {

    private PlotManager plotManager;

    @Override
    public void onEnable() {
        plotManager = new PlotManager(this);


        // DEBUG THINGS
        if (!plotManager.getPlotInformation(new PlotCoords(0, 0)).hasOwner()) {
            PlotInformation info = new PlotInformation("psycowithespn", new PlotCoords(0, 0), false, false);
            info.addEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 4));
            info.addEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 4));
            info.addDenied("psycowithespn");
            plotManager.registerPlot(info);
            plotManager.savePlotInformation(info);
        }

        File tycoonWorld = new File(getServer().getWorldContainer(), "tycoon");
        if (tycoonWorld.exists()) {
            recursiveDelete(tycoonWorld);
        }
        WorldCreator creator = new WorldCreator("tycoon");
        creator.generator(new TycoonChunkGen());
        tycoon = creator.createWorld();
        tycoon.setSpawnLocation(0, 64, 0);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new MovementListener(this), this);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        if (id.equals("tycoon")) {
            return new TycoonChunkGen();
        }
        return null;
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
        getServer().getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                event.getPlayer().teleport(tycoon.getSpawnLocation());
            }
        }, 5L);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        event.getPlayer().teleport(getServer().getWorld("world").getSpawnLocation());
    }

    public World getTycoonWorld() {
        return tycoon;
    }

    public PlotManager getPlotManager() {
        return plotManager;
    }
}
