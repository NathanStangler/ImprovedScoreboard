package net.improved.improvedscoreboard.api.events;

import net.improved.improvedscoreboard.api.Scoreboard;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public abstract class ScoreboardEvent extends Event implements Cancellable {
    private final Scoreboard scoreboard;
    private boolean cancelled;

    protected ScoreboardEvent(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
