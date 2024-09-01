package net.improved.improvedscoreboard.bukkit;

import net.improved.improvedscoreboard.api.Scoreboard;
import net.improved.improvedscoreboard.api.ScoreboardData;
import net.improved.improvedscoreboard.api.ScoreboardLine;
import net.improved.improvedscoreboard.api.component.AnimatedScoreboardComponent;
import net.improved.improvedscoreboard.api.component.ScoreboardComponent;
import net.improved.improvedscoreboard.common.Handler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class ImprovedScoreboard implements Scoreboard {
    private final String id = Long.toHexString(System.nanoTime());
    private final ScoreboardData data;
    private final Handler handler;
    private final boolean placeholder;
    private final Set<UUID> viewers = new HashSet<>();

    public ImprovedScoreboard(ScoreboardData data, Handler handler, boolean placeholder) {
        this.data = data;
        this.handler = handler;
        this.placeholder = placeholder;
    }

    public ScoreboardData getData() {
        return data;
    }

    public boolean show(Player player) {
        viewers.add(player.getUniqueId());
        handler.createObjective(player, id, Message.toComponent(player, data.getTitle().getText(), placeholder));

        for (byte i = 0; i < data.getLineCount(); i++) {
            ScoreboardLine line = data.getLines()[i];
            ScoreboardComponent component = line.getComponent();
            createLine(component, line.getID(), i, player);
        }

        handler.displayScoreboard(player, id);
        return true;
    }

    public boolean hide(Player player) {
        for (byte i = 0; i < data.getLineCount(); i++) {
            ScoreboardLine line = data.getLines()[i];
            removeLine(line.getID(), i, player);
        }

        handler.removeObjective(player, id);
        viewers.remove(player.getUniqueId());
        return true;
    }

    public void update() {
        updateTitle();
        for (byte i = 0; i < data.getLineCount(); i++) {
            ScoreboardLine line = data.getLines()[i];
            ScoreboardComponent component = line.getComponent();
            if (!line.isCreated()) {
                for (UUID viewer : viewers) {
                    createLine(component, line.getID(), i, Bukkit.getPlayer(viewer));
                }
                line.setCreated(true);
            } else {
                updateLine(component, line.getID());
            }
        }
        for (byte i = (byte) data.getLineCount(); i < 15; i++) {
            ScoreboardLine line = data.getLines()[i];
            if (line.isCreated()) {
                for (UUID viewer : viewers) {
                    removeLine(line.getID(), i, Bukkit.getPlayer(viewer));
                }
                line.setCreated(false);
            }
        }
        data.setDirty(false);
    }

    public void updateTitle() {
        ScoreboardComponent component = data.getTitle();
        if (component instanceof AnimatedScoreboardComponent animated) {
            animated.nextFrame();
        }
        viewers.forEach(viewer -> {
            Player player = Bukkit.getPlayer(viewer);
            handler.setTitle(player, id, Message.toComponent(player, component.getText(), placeholder));
        });
    }

    public void updateLine(byte score) {
        if (score < 0 || score >= data.getLineCount()) {
            throw new IllegalArgumentException("score is invalid");
        }
        ScoreboardLine line = data.getLines()[score];
        updateLine(line.getComponent(), line.getID());
    }

    public void updateLine(ScoreboardComponent component, String id) {
        if (component instanceof AnimatedScoreboardComponent animated) {
            animated.nextFrame();
        }
        viewers.forEach(viewer -> updateLine(component, id, Bukkit.getPlayer(viewer)));
    }

    private void createLine(ScoreboardComponent line, String id, byte score, Player player) {
        handler.createTeam(player, id, Message.toComponent(player, line.getText(), placeholder), score);
        handler.setScore(player, this.id, score);
    }

    private void updateLine(ScoreboardComponent line, String id, Player player) {
        handler.updateTeam(player, id, Message.toComponent(player, line.getText(), placeholder));
    }

    private void removeLine(String id, byte score, Player player) {
        handler.removeScore(player, this.id, score);
        handler.removeTeam(player, id);
    }

    public Set<UUID> getViewers() {
        return Collections.unmodifiableSet(viewers);
    }

    public boolean isViewer(Player player) {
        return isViewer(player.getUniqueId());
    }

    public boolean isViewer(UUID uuid) {
        return viewers.contains(uuid);
    }

    public String getID() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImprovedScoreboard other)) return false;
        return Objects.equals(getData(), other.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getData());
    }
}
