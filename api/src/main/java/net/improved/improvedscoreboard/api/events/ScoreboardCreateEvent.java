package net.improved.improvedscoreboard.api.events;

import net.improved.improvedscoreboard.api.Scoreboard;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ScoreboardCreateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Scoreboard scoreboard;

    public ScoreboardCreateEvent(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}