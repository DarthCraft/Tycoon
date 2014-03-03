package net.darthcraft.tycoon.plot;

import net.darthcraft.tycoon.PlotCoords;
import net.darthcraft.tycoon.PlotUtil;
import org.bukkit.potion.PotionEffect;

import java.util.HashSet;
import java.util.Set;

public class PlotInformation {

    private boolean globalOwned;
    private String owner;
    private PlotCoords coords;
    private Set<PotionEffect> effects;
    private Set<String> allowed;
    private Set<String> denied;

    public PlotInformation(String owner, PlotCoords coords, Set<PotionEffect> effects, Set<String> allowed, Set<String> denied, boolean globalOwned) {
        this.owner = owner;
        this.coords = coords;
        this.effects = effects;
        this.allowed = allowed;
        this.denied = denied;
        this.globalOwned = globalOwned;
    }

    public PlotInformation(String owner, PlotCoords coords, boolean globalOwned) {
        this(owner, coords, new HashSet<PotionEffect>(), new HashSet<String>(), new HashSet<String>(), globalOwned);
    }

    public PlotInformation(long hash, boolean globalOwned) {
        this(null, PlotUtil.plotCoordsFromHash(hash), globalOwned);
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
}
