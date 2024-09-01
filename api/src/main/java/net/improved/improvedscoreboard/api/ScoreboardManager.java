package net.improved.improvedscoreboard.api;

import org.bukkit.entity.Player;

import java.util.Collection;

public interface ScoreboardManager {
    /**
     * Creates a new scoreboard based on the passed data
     * @param data Data that will be linked to the scoreboard
     * @return The new scoreboard instance that was created or null if the name already exists
     */
    Scoreboard create(ScoreboardData data);

    /**
     * Deletes a scoreboard
     * @param name Name of the scoreboard
     * @return True if the scoreboard was successfully removed
     */
    boolean delete(String name);

    /**
     * Deletes a scoreboard
     * @param scoreboard Scoreboard that should be removed
     */
    void delete(Scoreboard scoreboard);

    /**
     * Shows the scoreboard to the player
     * @param player The player to show the scoreboard to
     * @param scoreboard The scoreboard to show the player
     * @return True if the scoreboard was successfully shown
     */
    boolean show(Player player, Scoreboard scoreboard);

    /**
     * Hides the active scoreboard from the player
     * @param player The player to hide the scoreboard from
     * @return True if the scoreboard was successfully hidden
     */
    boolean hide(Player player);

    /**
     * Reloads the scoreboards from the config
     */
    void reload();

    /**
     * Saves all registered scoreboards to the config
     */
    void save();

    /**
     * @param player The player to get active scoreboard of
     * @return Active scoreboard of player or null
     */
    Scoreboard getScoreboard(Player player);

    /**
     * @param name Name of the scoreboard to get
     * @return Scoreboard with requested name or null
     */
    Scoreboard getScoreboard(String name);

    /**
     * @return collection of scoreboards
     */
    Collection<Scoreboard> getScoreboards();

    /**
     * @return collection of scoreboards that are registered from config
     */
    Collection<Scoreboard> getPersistentScoreboards();
}
