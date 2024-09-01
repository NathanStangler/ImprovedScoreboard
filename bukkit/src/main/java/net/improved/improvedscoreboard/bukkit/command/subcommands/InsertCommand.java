package net.improved.improvedscoreboard.bukkit.command.subcommands;

import net.improved.improvedscoreboard.api.ImprovedScoreboardAPI;
import net.improved.improvedscoreboard.api.Scoreboard;
import net.improved.improvedscoreboard.api.component.StandardScoreboardComponent;
import net.improved.improvedscoreboard.bukkit.Message;
import net.improved.improvedscoreboard.bukkit.command.ScoreboardSubcommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InsertCommand implements ScoreboardSubcommand {
    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length < 4) {
            Message.HELP_INSERT.send(sender);
            return false;
        }

        String name = args[1];
        Scoreboard scoreboard = ImprovedScoreboardAPI.api().getScoreboardManager().getScoreboard(name);
        if (scoreboard == null) {
            Message.SCOREBOARD_DOES_NOT_EXIST.send(sender, name);
            return false;
        }

        int lines = scoreboard.getData().getLineCount();
        if (lines >= 15) {
            Message.MAX_LINES.send(sender);
            return false;
        }

        int line;

        try {
            line = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            Message.INVALID_LINE.send(sender, lines - 1);
            return false;
        }

        if (line < 0 || line > lines) {
            Message.INVALID_LINE.send(sender, lines - 1);
            return false;
        }

        String text = Arrays.stream(args).skip(3).collect(Collectors.joining(" "));
        scoreboard.getData().insertLine(new StandardScoreboardComponent(text, 20), line);
        Message.INSERT_SUCCESS.send(sender, text, line, name);
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 2) {
            return ImprovedScoreboardAPI.api().getScoreboardManager().getScoreboards().stream().map(scoreboard -> scoreboard.getData().getName()).filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase())).toList();
        }

        if (args.length == 3) {
            Scoreboard scoreboard = ImprovedScoreboardAPI.api().getScoreboardManager().getScoreboard(args[1]);
            if (scoreboard == null) {
                return Collections.emptyList();
            }

            return IntStream.range(0, scoreboard.getData().getLineCount()).mapToObj(Integer::toString).filter(name -> name.startsWith(args[2])).toList();
        }

        return Collections.emptyList();
    }
}
