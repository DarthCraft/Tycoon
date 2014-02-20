package net.darthcraft.tycoon;

import org.junit.Assert;
import org.junit.Test;

public class PlotHashTest {

    @Test
    public void test() {
        PlotCoords coords = new PlotCoords(12345, 54321);
        long hash = PlotHash.plotLocToHash(coords);
        PlotCoords nCods = PlotHash.plotCoordsFromHash(hash);
        Assert.assertEquals(coords, nCods);
    }

    @Test
    public void testZero() {
        PlotCoords coords = new PlotCoords(0, 0);
        long hash = PlotHash.plotLocToHash(coords);
        PlotCoords nCoords = PlotHash.plotCoordsFromHash(hash);
        Assert.assertEquals(coords, nCoords);
    }

    @Test
    public void testNegative() {
        PlotCoords coords = new PlotCoords(-12345, -54321);
        long hash = PlotHash.plotLocToHash(coords);
        PlotCoords nCoords = PlotHash.plotCoordsFromHash(hash);
        Assert.assertEquals(coords, nCoords);
    }
}
