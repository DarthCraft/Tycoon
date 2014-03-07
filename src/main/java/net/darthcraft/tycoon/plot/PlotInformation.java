package net.darthcraft.tycoon.plot;

import net.darthcraft.tycoon.PlotCoords;
import net.darthcraft.tycoon.PlotUtil;
import org.bukkit.potion.PotionEffect;

import java.util.HashSet;
import java.util.Set;

public class PlotInformation {

    private boolean globalOwned;
    private boolean globalProtected;
    private String owner;
    private PlotCoords coords;
    private Set<PotionEffect> effects;
    private Set<String> allowed;
    private Set<String> denied;

    public PlotInformation(String owner, PlotCoords coords, Set<PotionEffect> effects, Set<String> allowed, Set<String> denied, boolean globalOwned, boolean globalProtected) {
        this.owner = owner;
        this.coords = coords;
        this.effects = new HashSet<PotionEffect>();
        if (effects != null) {
            this.effects.addAll(effects);
        }
        this.allowed = new HashSet<String>();
        if (allowed != null) {
            this.allowed.addAll(allowed);
        }
        this.denied = new HashSet<String>();
        if (denied != null) {
            this.denied.addAll(denied);
        }
        this.globalOwned = globalOwned;
        this.globalProtected = globalProtected;
    }

    public PlotInformation(String owner, PlotCoords coords, boolean globalOwned, boolean globalProtected) {
        this(owner, coords, null, null, null, globalOwned, globalProtected);
    }

    public PlotInformation(long hash, boolean globalOwned, boolean globalProtected) {
        this(null, PlotUtil.plotCoordsFromHash(hash), globalOwned, globalProtected);
    }

    public boolean hasOwner() {
        return getOwner() != null;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public PlotCoords getCoords() {
        return coords;
    }

    public void setCoords(PlotCoords coords) {
        this.coords = coords;
    }

    public Set<PotionEffect> getEffects() {
        return effects;
    }

    public boolean addEffect(PotionEffect effect) {
        return this.effects.add(effect);
    }

    public boolean isAllowed(String player) {
        return allowed.contains(player.toLowerCase());
    }

    public Set<String> getAllowed() {
        return allowed;
    }

    public boolean addAllowed(String player) {
        return this.allowed.add(player.toLowerCase());
    }

    public boolean removeAllowed(String player) {
        return this.allowed.remove(player);
    }

    public boolean isDenied(String player) {
        return denied.contains(player.toLowerCase());
    }

    public Set<String> getDenied() {
        return denied;
    }

    public boolean addDenied(String player) {
        return this.denied.add(player.toLowerCase());
    }

    public boolean removeDenied(String player) {
        return this.denied.remove(player.toLowerCase());
    }

    public boolean isGlobalOwned() {
        return globalOwned;
    }

    public void setGlobalOwned(boolean globalOwned) {
        this.globalOwned = globalOwned;
    }

    public boolean isGlobalProtected() {
        return globalProtected;
    }

    public void setGlobalProtected(boolean globalProtected) {
        this.globalProtected = globalProtected;
    }
}
