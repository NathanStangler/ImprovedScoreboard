package net.improved.improvedscoreboard.bukkit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.improved.improvedscoreboard.api.Scoreboard;
import net.improved.improvedscoreboard.api.ScoreboardData;
import net.improved.improvedscoreboard.api.ScoreboardLine;
import net.improved.improvedscoreboard.api.ScoreboardManager;
import net.improved.improvedscoreboard.api.component.ScoreboardComponent;
import net.improved.improvedscoreboard.api.events.ScoreboardCreateEvent;
import net.improved.improvedscoreboard.api.events.ScoreboardDeleteEvent;
import net.improved.improvedscoreboard.api.events.ScoreboardHideEvent;
import net.improved.improvedscoreboard.api.events.ScoreboardShowEvent;
import net.improved.improvedscoreboard.common.Handler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ImprovedScoreboardManager implements ScoreboardManager {
    private final ImprovedScoreboardMain plugin;
    private final Handler handler;
    private final Cache<String, Long> cache;
    private final Map<String, Scoreboard> scoreboards = new ConcurrentHashMap<>();
    private int task = -1;

    public ImprovedScoreboardManager(ImprovedScoreboardMain plugin, Handler handler) {
        this.plugin = plugin;
        this.handler = handler;
        this.cache = CacheBuilder.newBuilder().expireAfterAccess(Duration.ofMinutes(3)).build();
    }

    public Scoreboard create(ScoreboardData data) {
        if (getScoreboard(data.getName()) != null) return null;
        Scoreboard scoreboard = new ImprovedScoreboard(data, handler, plugin.isPlaceholder());
        scoreboards.put(data.getName().toLowerCase(), scoreboard);
        new ScoreboardCreateEvent(scoreboard).callEvent();
        return scoreboard;
    }

    public boolean delete(String name) {
        Scoreboard scoreboard = getScoreboard(name);
        if (scoreboard == null) return false;
        delete(scoreboard);
        return true;
    }

    public void delete(Scoreboard scoreboard) {
        for (UUID uuid : scoreboard.getViewers()) {
            hide(Bukkit.getPlayer(uuid));
        }

        scoreboards.remove(scoreboard.getData().getName().toLowerCase());
        new ScoreboardDeleteEvent(scoreboard, Set.copyOf(scoreboard.getViewers())).callEvent();
    }

    public boolean show(Player player, Scoreboard scoreboard) {
        if (!hide(player)) return false;
        if (!new ScoreboardShowEvent(scoreboard, player).callEvent()) return false;
        return ((ImprovedScoreboard) scoreboard).show(player);
    }

    public boolean hide(Player player) {
        Scoreboard scoreboard = getScoreboard(player);
        if (scoreboard == null) return true;
        if (!new ScoreboardHideEvent(scoreboard, player).callEvent()) return false;
        return ((ImprovedScoreboard) scoreboard).hide(player);
    }

    public void load() {
        for (ScoreboardData data : plugin.getScoreboardConfig().readScoreboards()) {
            create(data);
        }

        plugin.getConditionManager().load();
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (getScoreboard(player) == null) {
                Scoreboard scoreboard = plugin.getConditionManager().getScoreboard(player);
                if (scoreboard != null) {
                    show(player, scoreboard);
                }
            }
        });
    }

    public void reload() {
        for (Scoreboard scoreboard : getPersistentScoreboards()) {
            for (UUID uuid : scoreboard.getViewers()) {
                ((ImprovedScoreboard) scoreboard).hide(Bukkit.getPlayer(uuid));
            }
            scoreboards.remove(scoreboard.getData().getName().toLowerCase());
        }
        load();
    }

    private boolean shouldUpdate(String id, int delay, long time) {
        if (delay < 1) return false;
        Long old = cache.asMap().get(id);
        return old == null || time > (old + delay);
    }

    public void open() {
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            long time = System.currentTimeMillis();
            for (Scoreboard scoreboard : getScoreboards()) {
                ScoreboardData data = scoreboard.getData();
                if (data.isDirty()) {
                    scoreboard.update();
                } else {
                    ScoreboardComponent title = data.getTitle();
                    String id = ((ImprovedScoreboard) scoreboard).getID();
                    if (title.isDirty() || shouldUpdate(id, title.getDelay(), time)) {
                        scoreboard.updateTitle();
                        title.setDirty(false);
                        cache.put(id, time);
                    }
                    for (byte i = 0; i < data.getLineCount(); i++) {
                        ScoreboardLine line = data.getLines()[i];
                        ScoreboardComponent component = line.getComponent();
                        if (component.isDirty() || shouldUpdate(line.getID(), component.getDelay(), time)) {
                            scoreboard.updateLine(i);
                            component.setDirty(false);
                            cache.put(line.getID(), time);
                        }
                    }
                }
            }
        }, 0L, plugin.getScoreboardConfig().readMinimumDelay()).getTaskId();
    }

    public void save() {
        plugin.getScoreboardConfig().saveScoreboards(getScoreboards());
    }

    public void close() {
        if (task != -1) {
            Bukkit.getScheduler().cancelTask(task);
            task = -1;
        }
        cache.invalidateAll();

        save();

        for (Scoreboard scoreboard : getScoreboards()) {
            for (UUID uuid : scoreboard.getViewers()) {
                ((ImprovedScoreboard) scoreboard).hide(Bukkit.getPlayer(uuid));
            }
        }

        scoreboards.clear();
    }

    public Scoreboard getScoreboard(Player player) {
        for (Scoreboard scoreboard : getScoreboards()) {
            if (scoreboard.getViewers().contains(player.getUniqueId())) {
                return scoreboard;
            }
        }
        return null;
    }

    public Scoreboard getScoreboard(String name) {
        return scoreboards.get(name);
    }

    public Collection<Scoreboard> getScoreboards() {
        return Collections.unmodifiableCollection(scoreboards.values());
    }

    public Collection<Scoreboard> getPersistentScoreboards() {
        return scoreboards.values().stream().filter(scoreboard -> scoreboard.getData().isPersistent()).toList();
    }
}
