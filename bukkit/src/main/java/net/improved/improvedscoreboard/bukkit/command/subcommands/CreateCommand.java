package net.improved.improvedscoreboard.bukkit.command.subcommands;

import net.improved.improvedscoreboard.api.ImprovedScoreboardAPI;
import net.improved.improvedscoreboard.api.ScoreboardData;
import net.improved.improvedscoreboard.bukkit.Message;
import net.improved.improvedscoreboard.bukkit.command.ScoreboardSubcommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class CreateCommand implements ScoreboardSubcommand {

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length < 2) {
            Message.HELP_CREATE.send(sender);
            return false;
        }

        String name = args[1];
        if (ImprovedScoreboardAPI.api().getScoreboardManager().getScoreboard(name) != null) {
            Message.CREATE_ALREADY_EXISTS.send(sender, name);
            return false;
        }

        ScoreboardData data = new ScoreboardData(name);
        data.setPersistent(true);
        ImprovedScoreboardAPI.api().getScoreboardManager().create(data);
        Message.CREATE_SUCCESS.send(sender, name);
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
