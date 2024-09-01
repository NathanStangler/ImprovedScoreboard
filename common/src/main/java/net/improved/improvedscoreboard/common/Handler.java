package net.improved.improvedscoreboard.common;


import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public interface Handler {
    void createObjective(Player player, String id, Component title);

    void removeObjective(Player player, String id);

    void setTitle(Player player, String id, Component title);

    void displayScoreboard(Player player, String id);

    void setScore(Player player, String id, byte score);

    void removeScore(Player player, String id, byte score);

    void createTeam(Player player, String id, Component text, byte score);

    void updateTeam(Player player, String id, Component text);

    void removeTeam(Player player, String id);
}
