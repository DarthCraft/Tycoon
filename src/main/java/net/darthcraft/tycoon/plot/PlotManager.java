package net.darthcraft.tycoon.plot;

import net.darthcraft.tycoon.PlotCoords;
import net.darthcraft.tycoon.PlotUtil;
import net.darthcraft.tycoon.Tycoon;

import java.util.HashMap;
import java.util.Map;

public class PlotManager {

    private final Tycoon tycoon;
    private Map<Long, PlotInformation> registeredPlots;

    public PlotManager(Tycoon tycoon) {
        this.tycoon = tycoon;
        this.registeredPlots = new HashMap<Long, PlotInformation>();
    }

    public PlotInformation getPlotInformation(long hash) {
        PlotInformation info = registeredPlots.get(hash);
        if (info == null) {
            info = new PlotInformation(hash, false);
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
        return true;
    }
}
