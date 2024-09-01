package net.improved.improvedscoreboard.bukkit;

import net.improved.improvedscoreboard.api.ImprovedScoreboardAPI;
import net.improved.improvedscoreboard.api.ScoreboardManager;
import net.improved.improvedscoreboard.bukkit.command.ScoreboardCommand;
import net.improved.improvedscoreboard.common.Handler;
import net.improved.improvedscoreboard.v1_20_1.Handler1_20_1;
import net.improved.improvedscoreboard.v1_20_2.Handler1_20_2;
import net.improved.improvedscoreboard.v1_20_4.Handler1_20_4;
import net.improved.improvedscoreboard.v1_20_6.Handler1_20_6;
import net.improved.improvedscoreboard.v1_21.Handler1_21;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ImprovedScoreboardMain extends JavaPlugin implements ImprovedScoreboardAPI {
    private boolean placeholder = true;
    private ImprovedScoreboardManager scoreboardManager;
    private ImprovedScoreboardConfig scoreboardConfig;
    private ImprovedConditionManager conditionManager;

    @Override
    public void onEnable() {
        Handler handler = switch (getServer().getMinecraftVersion()) {
            case "1.21" -> new Handler1_21();
            case "1.20.6" -> new Handler1_20_6();
            case "1.20.4" -> new Handler1_20_4();
            case "1.20.2" -> new Handler1_20_2();
            case "1.20.1" -> new Handler1_20_1();
            default -> null;
        };

        if (handler == null) {
            getLogger().severe("""
                    
                    ----------------------------------------------------------
                    Server version unsupported, disabling plugin.
                    Update server to 1.20.1, 1.20.2, 1.20.4, 1.20.6, or 1.21.
                    ----------------------------------------------------------
                    """);
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().warning("PlaceholderAPI is not enabled. Placeholders will not work unless it is enabled.");
            this.placeholder = false;
        }

        this.scoreboardManager = new ImprovedScoreboardManager(this, handler);
        this.scoreboardConfig = new ImprovedScoreboardConfig(this);
        this.conditionManager = new ImprovedConditionManager(this);
        scoreboardManager.open();
        scoreboardManager.load();
        getServer().getCommandMap().register("improvedscoreboard", new ScoreboardCommand(this));
        getServer().getPluginManager().registerEvents(new ScoreboardListener(this), this);
    }

    @Override
    public void onDisable() {
        if (scoreboardManager != null) scoreboardManager.close();
    }

    public boolean isPlaceholder() {
        return placeholder;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public ImprovedScoreboardConfig getScoreboardConfig() {
        return scoreboardConfig;
    }

    public ImprovedConditionManager getConditionManager() {
        return conditionManager;
    }
}
