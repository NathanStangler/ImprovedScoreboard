package net.improved.improvedscoreboard.bukkit.command.subcommands;

import net.improved.improvedscoreboard.api.ImprovedScoreboardAPI;
import net.improved.improvedscoreboard.api.Scoreboard;
import net.improved.improvedscoreboard.bukkit.Message;
import net.improved.improvedscoreboard.bukkit.command.ScoreboardSubcommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class ListCommand implements ScoreboardSubcommand {

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        Collection<Scoreboard> scoreboards = ImprovedScoreboardAPI.api().getScoreboardManager().getScoreboards();
        if (scoreboards.size() == 0) {
            Message.LIST_NONE.send(sender);
            return true;
        }

        int page = 1;
        if (args.length > 1) {
            try {
                page = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                Message.LIST_INVALID_PAGE.send(sender);
                return false;
            }
        }

        int pages = (scoreboards.size() / 10) + 1;
        if (page <= 0 || page > pages) {
            Message.LIST_INVALID_PAGE.send(sender);
            return false;
        }

        Message.LIST_TITLE.send(sender, page, pages);
        scoreboards.stream().skip((page - 1) * 10).limit(10).forEach(scoreboard -> Message.LIST_ENTRY.send(sender, scoreboard.getData().getName()));
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 2) {
            int scoreboards = ImprovedScoreboardAPI.api().getScoreboardManager().getScoreboards().size();
            return IntStream.range(1, (scoreboards / 10) + 2).mapToObj(Integer::toString).filter(name -> name.startsWith(args[1])).toList();
        }

        return Collections.emptyList();
    }
}
