package net.improved.improvedscoreboard.bukkit.command.subcommands;

import net.improved.improvedscoreboard.api.ImprovedScoreboardAPI;
import net.improved.improvedscoreboard.api.Scoreboard;
import net.improved.improvedscoreboard.api.component.AnimatedScoreboardComponent;
import net.improved.improvedscoreboard.api.component.ScoreboardComponent;
import net.improved.improvedscoreboard.api.component.StandardScoreboardComponent;
import net.improved.improvedscoreboard.bukkit.Message;
import net.improved.improvedscoreboard.bukkit.command.ScoreboardSubcommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TitleCommand implements ScoreboardSubcommand {
    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length < 3) {
            Message.HELP_TITLE.send(sender);
            return false;
        }

        String name = args[1];
        Scoreboard scoreboard = ImprovedScoreboardAPI.api().getScoreboardManager().getScoreboard(name);
        if (scoreboard == null) {
            Message.SCOREBOARD_DOES_NOT_EXIST.send(sender, name);
            return false;
        }

        String title = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));
        ScoreboardComponent component = scoreboard.getData().getTitle();
        if (component instanceof AnimatedScoreboardComponent animated) {
            animated.setText(List.of(title));
        } else {
            ((StandardScoreboardComponent) component).setText(title);
        }
        Message.TITLE_SUCCESS.send(sender, name, title);
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
