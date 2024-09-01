package net.improved.improvedscoreboard.api.events;

import net.improved.improvedscoreboard.api.Scoreboard;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ScoreboardShowEvent extends ScoreboardEvent {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;

    public ScoreboardShowEvent(Scoreboard scoreboard, Player player) {
        super(scoreboard);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
