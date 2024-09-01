package net.improved.improvedscoreboard.bukkit.command.subcommands;

import net.improved.improvedscoreboard.api.ImprovedScoreboardAPI;
import net.improved.improvedscoreboard.bukkit.Message;
import net.improved.improvedscoreboard.bukkit.command.ScoreboardSubcommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class DeleteCommand implements ScoreboardSubcommand {
    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length < 2) {
            Message.HELP_DELETE.send(sender);
            return false;
        }

        String name = args[1];
        if (!ImprovedScoreboardAPI.api().getScoreboardManager().delete(name)) {
            Message.DELETE_DOES_NOT_EXISTS.send(sender, name);
            return false;
        }

        Message.DELETE_SUCCESS.send(sender, name);
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 2) {
            return ImprovedScoreboardAPI.api().getScoreboardManager().getScoreboards().stream().map(scoreboard -> scoreboard.getData().getName()).filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase())).toList();
        }

        return Collections.emptyList();
    }
}
