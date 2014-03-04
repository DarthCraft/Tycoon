package net.darthcraft.tycoon.plot;

import net.darthcraft.tycoon.PlotCoords;
import net.darthcraft.tycoon.PlotUtil;
import net.darthcraft.tycoon.Tycoon;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PlotManager {

    private final Tycoon tycoon;
    private final File saveFile;
    private Map<Long, PlotInformation> registeredPlots;

    public PlotManager(Tycoon tycoon) {
        this.tycoon = tycoon;
        this.registeredPlots = new HashMap<Long, PlotInformation>();
        this.saveFile = new File(tycoon.getDataFolder(), "plots.bin");
        load();
    }

    public synchronized PlotInformation getPlotInformation(long hash) {
        PlotInformation info = registeredPlots.get(hash);
        if (info == null) {
            info = new PlotInformation(hash, false);
        }
        return info;
    }

    public synchronized PlotInformation getPlotInformation(PlotCoords coords) {
        return getPlotInformation(PlotUtil.plotLocToHash(coords));
    }

    public synchronized boolean registerPlot(PlotInformation info) {
        long hash = PlotUtil.plotLocToHash(info.getCoords());
        if (registeredPlots.containsKey(hash)) {
            return false;
        }
        registeredPlots.put(hash, info);
        return true;
    }

    public synchronized void savePlotInfo() {
//        tycoon.getServer().getScheduler().runTaskAsynchronously(tycoon, new Runnable() {
//            @Override
//            public void run() {
                save();
//            }
//        });
    }

    private synchronized void save() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(saveFile));
            out.writeObject(registeredPlots);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void load() {
        if(!saveFile.exists()) {
            saveFile.getParentFile().mkdirs();
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(saveFile));
            registeredPlots = (Map<Long, PlotInformation>) in.readObject();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
