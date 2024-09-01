package net.improved.improvedscoreboard.api.events;

import net.improved.improvedscoreboard.api.Scoreboard;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.UUID;

public class ScoreboardDeleteEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Scoreboard scoreboard;
    private final Set<UUID> viewers;

    public ScoreboardDeleteEvent(Scoreboard scoreboard, Set<UUID> viewers) {
        this.scoreboard = scoreboard;
        this.viewers = viewers;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public Set<UUID> getViewers() {
        return viewers;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}