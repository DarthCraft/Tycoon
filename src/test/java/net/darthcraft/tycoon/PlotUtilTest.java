package net.darthcraft.tycoon;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlotUtilTest {

    @Test
    public void test() {
        PlotCoords coords = new PlotCoords(12345, 54321);
        long hash = PlotUtil.plotLocToHash(coords);
        PlotCoords nCods = PlotUtil.plotCoordsFromHash(hash);
        assertEquals(coords, nCods);
    }

    @Test
    public void testZero() {
        PlotCoords coords = new PlotCoords(0, 0);
        long hash = PlotUtil.plotLocToHash(coords);
        PlotCoords nCoords = PlotUtil.plotCoordsFromHash(hash);
        assertEquals(coords, nCoords);
    }

    @Test
    public void testNegative() {
        PlotCoords coords = new PlotCoords(-12345, -54321);
        long hash = PlotUtil.plotLocToHash(coords);
        PlotCoords nCoords = PlotUtil.plotCoordsFromHash(hash);
        assertEquals(coords, nCoords);
    }

    @Test
    public void testWorldCoords() {
        PlotCoords coords = new PlotCoords(0, 0);
        assertEquals(coords, PlotUtil.worldCoordsToPlotCoords(10, 20));
        coords = new PlotCoords(-1, 0);
        assertEquals(coords, PlotUtil.worldCoordsToPlotCoords(-10, 20));
        coords = new PlotCoords(-1, -1);
        assertEquals(coords, PlotUtil.worldCoordsToPlotCoords(-10, -20));
        coords = new PlotCoords(0, -1);
        assertEquals(coords, PlotUtil.worldCoordsToPlotCoords(10, -20));
        coords = new PlotCoords(1, 0);
        assertEquals(coords, PlotUtil.worldCoordsToPlotCoords(50, 20));
    }
}
