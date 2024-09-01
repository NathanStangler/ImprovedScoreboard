package net.improved.improvedscoreboard.bukkit.command;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ScoreboardSubcommand {
    boolean execute(@NotNull CommandSender sender, @NotNull String[] args);

    @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args);
}
