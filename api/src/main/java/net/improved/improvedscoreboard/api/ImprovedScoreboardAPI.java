package net.improved.improvedscoreboard.api;

import org.bukkit.Bukkit;

public interface ImprovedScoreboardAPI {
    static ImprovedScoreboardAPI api() {
        if (isEnabled()) {
            return ImprovedScoreboardProvider.getAPI();
        }

        throw new NullPointerException("ImprovedScoreboardAPI is not hooked");
    }

    static boolean isEnabled() {
        return ImprovedScoreboardProvider.isEnabled();
    }

    ScoreboardManager getScoreboardManager();

    class ImprovedScoreboardProvider {
        private static boolean enabled;
        private static ImprovedScoreboardAPI api;

        public static boolean isEnabled() {
            if (enabled) return true;

            if (Bukkit.getPluginManager().isPluginEnabled("ImprovedScoreboard")) {
                ImprovedScoreboardAPI plugin = (ImprovedScoreboardAPI) Bukkit.getPluginManager().getPlugin("ImprovedScoreboard");
                if (plugin == null) return false;
                enabled = true;
                api = plugin;
            }

            return enabled;
        }

        public static ImprovedScoreboardAPI getAPI() {
            return api;
        }
    }
}
