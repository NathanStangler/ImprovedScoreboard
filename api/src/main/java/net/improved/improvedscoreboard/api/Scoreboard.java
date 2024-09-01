package net.improved.improvedscoreboard.api;

import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public interface Scoreboard {
    /**
     * @return data linked to the scoreboard
     */
    ScoreboardData getData();

    /**
     * Immediately updates the scoreboard
     */
    void update();

    /**
     * Immediately updates the passed line
     * @param line Line number to update
     */
    void updateLine(byte line);

    /**
     * Immediately updates the title
     */
    void updateTitle();

    /**
     * @return set of viewers uuids
     */
    Set<UUID> getViewers();

    /**
     * @param player The player to check
     * @return True when the scoreboard is shown to the player
     */
    boolean isViewer(Player player);

    /**
     * @param uuid The uuid to check
     * @return True when the scoreboard is shown to the player
     */
    boolean isViewer(UUID uuid);
}
