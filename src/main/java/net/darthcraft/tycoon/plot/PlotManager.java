package net.darthcraft.tycoon.plot;

import net.darthcraft.tycoon.PlotCoords;
import net.darthcraft.tycoon.PlotUtil;
import net.darthcraft.tycoon.Tycoon;
import net.darthcraft.tycoon.plot.file.PlotFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlotManager {

    private static final String GLOBAL_NAME = "**GLOBAL**";

    private final Pattern PLOT_PATTERN = Pattern.compile("([-]?\\d+),([-]?\\d+).yml");
    private final File plotDir;
    private final Tycoon tycoon;
    private final Map<Long, PlotInformation> registeredPlots;
    private final Map<String, List<PlotInformation>> playerLink;

    public PlotManager(Tycoon tycoon) {
        this.tycoon = tycoon;
        this.registeredPlots = new HashMap<Long, PlotInformation>();
        this.playerLink = new HashMap<String, List<PlotInformation>>();
        plotDir = new File(tycoon.getDataFolder(), "plots");
        if (!plotDir.exists()) {
            plotDir.mkdirs();
        }
        load();
    }

    public PlotInformation getPlotInformation(long hash) {
        PlotInformation info = registeredPlots.get(hash);
        if (info == null) {
            info = new PlotInformation(hash, false, false);
        }
        return info;
    }

    public PlotInformation getPlotInformation(PlotCoords coords) {
        return getPlotInformation(PlotUtil.plotLocToHash(coords));
    }

    public boolean registerPlot(PlotInformation info) {
        long hash = PlotUtil.plotLocToHash(info.getCoords());
        if (registeredPlots.containsKey(hash)) {
            return false;
        }
        registeredPlots.put(hash, info);
        if (info.isGlobalOwned()) {
            addPlotToPlayer(GLOBAL_NAME, info);
        } else {
            addPlotToPlayer(info.getOwner(), info);
        }
        return true;
    }

    private void addPlotToPlayer(String player, PlotInformation info) {
        List<PlotInformation> infos = playerLink.get(player);
        if (infos == null) {
            infos = new ArrayList<PlotInformation>();
            playerLink.put(player, infos);
        }
        infos.add(info);
    }

    private void load() {
        for (File plotFile : plotDir.listFiles()) {
            Matcher matcher = PLOT_PATTERN.matcher(plotFile.getName());
            if (!matcher.matches()) {
                continue;
            }
            int pX = Integer.parseInt(matcher.group(1));
            int pZ = Integer.parseInt(matcher.group(2));
            PlotInformation info = PlotFile.fileToPlotInformation(plotFile, pX, pZ);
            if (info == null) {
                System.err.println("Failed to load plot info for file " + pX + "," + pZ + ".yml");
                continue;
            }
            if(!registerPlot(info)) {
                System.err.println("Duplicate plot location: " + pX + "," + pZ);
            }
        }
    }

    public void savePlotInformation(PlotInformation info) {
        PlotFile.savePlotInformation(plotDir, info);
    }
}
