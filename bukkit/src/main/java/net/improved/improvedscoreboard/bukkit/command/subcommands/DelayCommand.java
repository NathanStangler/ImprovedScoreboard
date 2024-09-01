package net.improved.improvedscoreboard.bukkit.command.subcommands;

import net.improved.improvedscoreboard.api.ImprovedScoreboardAPI;
import net.improved.improvedscoreboard.api.Scoreboard;
import net.improved.improvedscoreboard.api.component.ScoreboardComponent;
import net.improved.improvedscoreboard.bukkit.Message;
import net.improved.improvedscoreboard.bukkit.command.ScoreboardSubcommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DelayCommand implements ScoreboardSubcommand {
    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length < 4) {
            Message.HELP_DELAY.send(sender);
            return false;
        }

        String name = args[1];
        Scoreboard scoreboard = ImprovedScoreboardAPI.api().getScoreboardManager().getScoreboard(name);
        if (scoreboard == null) {
            Message.SCOREBOARD_DOES_NOT_EXIST.send(sender, name);
            return false;
        }

        ScoreboardComponent component;
        if (args[2].equalsIgnoreCase("title")) {
            component = scoreboard.getData().getTitle();
        } else {
            int lines = scoreboard.getData().getLineCount() - 1;
            int line;

            try {
                line = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                Message.INVALID_LINE.send(sender, lines);
                return false;
            }

            if (line < 0 || line > lines) {
                Message.INVALID_LINE.send(sender, lines);
                return false;
            }

            component = scoreboard.getData().getLine(line);
        }

        int delay;
        try {
            delay = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            Message.DELAY_DURATION.send(sender);
            return false;
        }

        component.setDelay(delay);
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

            return Stream.concat(Stream.of("title"), IntStream.range(0, scoreboard.getData().getLineCount()).mapToObj(Integer::toString)).filter(name -> name.toLowerCase().startsWith(args[2].toLowerCase())).toList();
        }

        return Collections.emptyList();
    }
}
