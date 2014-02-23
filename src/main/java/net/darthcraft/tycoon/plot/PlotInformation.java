package net.darthcraft.tycoon.plot;

import net.darthcraft.tycoon.PlotCoords;
import org.bukkit.potion.PotionEffect;

import java.util.Set;

public class PlotInformation {

    private String owner;
    private PlotCoords coords;
    private Set<PotionEffect> effects;
    private Set<String> allowed;
    private Set<String> denied;
}
