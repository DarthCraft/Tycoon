package net.darthcraft.tycoon.plot.file;

import net.darthcraft.tycoon.PlotCoords;
import net.darthcraft.tycoon.plot.PlotInformation;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlotFile {

    private static final Pattern POTION_PATTERN = Pattern.compile("([A-Z_]+),(\\d+)");

    private static final String OWNER = "owner";
    private static final String GLOBAL_OWNED = "global-owned";
    private static final String GLOBAL_PROTECTED = "global-protected";
    private static final String PLOT_EFFECTS = "plot-effects";
    private static final String ALLOWED = "allowed";
    private static final String DENIED = "denied";


    public static PlotInformation fileToPlotInformation(File file, int pX, int pZ) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        List<String> plotEffects = config.getStringList(PLOT_EFFECTS);
        Set<PotionEffect> potionEffectsSet = new HashSet<PotionEffect>();
        for (String potion : plotEffects) {
            PotionEffect effect = parsePotionEffect(potion);
            if (effect != null) {
                potionEffectsSet.add(parsePotionEffect(potion));
            }
        }

        boolean globalOwned = config.getBoolean(GLOBAL_OWNED);
        String owner = null;
        Set<String> deniedSet = null;
        Set<String> allowedSet = null;
        boolean globalProtected = false;

        if (globalOwned) {
            globalProtected = config.getBoolean(GLOBAL_PROTECTED);
        } else {
            owner = config.getString(OWNER);
            List<String> allowed = config.getStringList(ALLOWED);
            List<String> denied = config.getStringList(ALLOWED);
            allowedSet = new HashSet<String>(allowed);
            deniedSet = new HashSet<String>(denied);
        }

        PlotCoords coords = new PlotCoords(pX, pZ);
        return new PlotInformation(owner, coords, potionEffectsSet, allowedSet, deniedSet, globalOwned, globalProtected);
    }

    public static boolean savePlotInformation(File plotDir, PlotInformation info) {
        File plotFile = new File(plotDir, info.getCoords().getPlotX() + "," + info.getCoords().getPlotZ() + ".yml");
        if (!info.hasOwner() && !info.isGlobalOwned()) {
            plotFile.delete();
            return true;
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(plotFile);
        List<String> potionEffects = new ArrayList<String>();
        for (PotionEffect effect : info.getEffects()) {
            potionEffects.add(potionEffectToString(effect));
        }

        Boolean globalProtected = null;
        String owner = null;
        List<String> allowed = null;
        List<String> denied = null;
        if (info.isGlobalOwned()) {
            globalProtected = info.isGlobalProtected();
        } else {
            owner = info.getOwner();
            allowed = new ArrayList<String>(info.getAllowed());
            denied = new ArrayList<String>(info.getDenied());
        }

        config.set(GLOBAL_OWNED, info.isGlobalOwned());
        config.set(GLOBAL_PROTECTED, globalProtected);
        config.set(OWNER, owner);
        config.set(ALLOWED, allowed);
        config.set(DENIED, denied);
        config.set(PLOT_EFFECTS, potionEffects);
        try {
            config.save(plotFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static PotionEffect parsePotionEffect(String potion) {
        Matcher matcher = POTION_PATTERN.matcher(potion);
        if (matcher.matches()) {
            PotionEffectType type = PotionEffectType.getByName(matcher.group(1));
            int amp = Integer.parseInt(matcher.group(2));
            return new PotionEffect(type, Integer.MAX_VALUE, amp);
        } else {
            return null;
        }
    }

    private static String potionEffectToString(PotionEffect effect) {
        return effect.getType().getName() + "," + effect.getAmplifier();
    }
}
