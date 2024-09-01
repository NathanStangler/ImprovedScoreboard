package net.improved.improvedscoreboard.bukkit;

import net.improved.improvedscoreboard.api.Scoreboard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreboardListener implements Listener {
    private final ImprovedScoreboardMain plugin;

    public ScoreboardListener(ImprovedScoreboardMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (plugin.getScoreboardManager().getScoreboard(e.getPlayer()) != null) {
            return;
        }

        Scoreboard scoreboard = plugin.getConditionManager().getScoreboard(e.getPlayer());
        if (scoreboard != null) {
            plugin.getScoreboardManager().show(e.getPlayer(), scoreboard);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        plugin.getScoreboardManager().hide(e.getPlayer());
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        Scoreboard old = plugin.getScoreboardManager().getScoreboard(e.getPlayer());
        if (old == null || old.getData().isPersistent()) {
            Scoreboard scoreboard = plugin.getConditionManager().getScoreboard(e.getPlayer());
            if (scoreboard == null) {
                plugin.getScoreboardManager().hide(e.getPlayer());
            }
            plugin.getScoreboardManager().show(e.getPlayer(), scoreboard);
        }
    }
}
