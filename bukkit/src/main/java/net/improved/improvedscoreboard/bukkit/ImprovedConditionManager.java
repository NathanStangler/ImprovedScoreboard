package net.improved.improvedscoreboard.bukkit;

import net.improved.improvedscoreboard.api.Scoreboard;
import org.bukkit.entity.Player;

import java.util.Map;

public class ImprovedConditionManager {
    private final ImprovedScoreboardMain plugin;
    private String base;
    private Map<String, String> worlds;

    public ImprovedConditionManager(ImprovedScoreboardMain plugin) {
        this.plugin = plugin;
        load();
    }

    public void load() {
        base = plugin.getScoreboardConfig().readBaseScoreboard();
        worlds = plugin.getScoreboardConfig().readWorldScoreboards();
    }

    public Scoreboard getScoreboard(Player player) {
        Scoreboard scoreboard = getWorldScoreboard(player.getWorld().getName());
        if (scoreboard != null) {
            return scoreboard;
        }
        return getBaseScoreboard();
    }

    public String getBaseScoreboardName() {
        return base;
    }

    public Scoreboard getBaseScoreboard() {
        return plugin.getScoreboardManager().getScoreboard(base);
    }

    public String getWorldScoreboardName(String world) {
        return worlds.get(world);
    }

    public Scoreboard getWorldScoreboard(String world) {
        String name = getWorldScoreboardName(world);
        if (name == null) return null;
        return plugin.getScoreboardManager().getScoreboard(name);
    }
}
