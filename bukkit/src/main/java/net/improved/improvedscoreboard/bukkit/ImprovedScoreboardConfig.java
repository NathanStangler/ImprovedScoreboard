package net.improved.improvedscoreboard.bukkit;

import net.improved.improvedscoreboard.api.Scoreboard;
import net.improved.improvedscoreboard.api.ScoreboardData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ImprovedScoreboardConfig {
    private File file;

    public ImprovedScoreboardConfig(ImprovedScoreboardMain plugin) {
        if (!plugin.getDataFolder().exists() && !plugin.getDataFolder().mkdirs()) {
            plugin.getLogger().severe("Failed to load configuration files");
            return;
        }

        this.file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists() && plugin.getResource(file.getName()) != null) {
            plugin.saveResource(file.getName(), true);
        }
    }

    public long readMinimumDelay() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getLong("minimum-update-delay", 20L);
    }

    public String readBaseScoreboard() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getString("base-scoreboard");
    }

    public Map<String, String> readWorldScoreboards() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        Map<String, String> scoreboards = new HashMap<>();
        ConfigurationSection section = config.getConfigurationSection("world-based-scoreboards");
        if (section == null) {
            return scoreboards;
        }

        for (String name : section.getKeys(false)) {
            scoreboards.put(name, section.getString(name));
        }

        return scoreboards;
    }

    public List<ScoreboardData> readScoreboards() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        List<ScoreboardData> data = new ArrayList<>();
        ConfigurationSection scoreboards = config.getConfigurationSection("scoreboards");
        if (scoreboards == null) {
            return data;
        }

        for (String name : scoreboards.getKeys(false)) {
            ConfigurationSection section = scoreboards.getConfigurationSection(name);
            if (section == null) {
                continue;
            }
            data.add(ScoreboardData.read(section));
        }

        return data;
    }

    public void saveScoreboards(Collection<Scoreboard> scoreboards) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("scoreboards", null);
        for (Scoreboard scoreboard : scoreboards) {
            writeScoreboard(config, scoreboard);
        }

        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save configuration file", e);
        }
    }

    private void writeScoreboard(YamlConfiguration config, Scoreboard scoreboard) {
        ConfigurationSection scoreboards = config.getConfigurationSection("scoreboards");
        if (scoreboards == null) {
            scoreboards = config.createSection("scoreboards");
        }

        String name = scoreboard.getData().getName();
        ConfigurationSection section = scoreboards.getConfigurationSection(name);
        if (section == null) {
            section = scoreboards.createSection(name);
        }

        scoreboard.getData().write(section);
    }
}
