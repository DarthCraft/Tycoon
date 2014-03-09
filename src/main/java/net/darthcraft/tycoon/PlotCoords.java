package net.darthcraft.tycoon;

import java.io.Serializable;

public class PlotCoords implements Serializable {

    private static final long serialVersionUID = 123735762457373L;

    private final int plotX;
    private final int plotZ;

    public PlotCoords(int plotX, int plotZ) {
        this.plotX = plotX;
        this.plotZ = plotZ;
    }

    public int getPlotX() {
        return plotX;
    }

    public int getPlotZ() {
        return plotZ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlotCoords that = (PlotCoords) o;

        if (plotX != that.plotX) return false;
        if (plotZ != that.plotZ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = plotX;
        result = 31 * result + plotZ;
        return result;
    }
}
