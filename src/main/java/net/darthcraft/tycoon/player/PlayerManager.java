package net.darthcraft.tycoon.player;

import net.darthcraft.tycoon.Tycoon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager implements Listener {

    private final Tycoon plugin;
    private final Map<String, PlayerInfo> players;
    private boolean initialized;

    public PlayerManager(Tycoon plugin) {
        this.plugin = plugin;
        this.players = new HashMap<String, PlayerInfo>();
    }

    public void initialize() {
        if (initialized) {
            return;
        }
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        initialized = true;
    }

    public PlayerInfo getPlayerInfo(Player player) {
        return players.get(player.getName());
    }

    private void registerPlayerData(Player player) {
        PlayerInfo info = new PlayerInfo(plugin, player.getName());
        players.put(player.getName(), info);
    }

    private void unregisterPlayerData(Player player) {
        players.remove(player.getName());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (event == null || event.getPlayer() == null) {
            return;
        }
        registerPlayerData(event.getPlayer());
        getPlayerInfo(event.getPlayer()).onLogin();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (event == null || event.getPlayer() == null) {
            return;
        }
        getPlayerInfo(event.getPlayer()).onLogout();
    }
}
