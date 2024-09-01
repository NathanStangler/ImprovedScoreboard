package net.improved.improvedscoreboard.bukkit.command.subcommands;

import net.improved.improvedscoreboard.api.ImprovedScoreboardAPI;
import net.improved.improvedscoreboard.api.Scoreboard;
import net.improved.improvedscoreboard.bukkit.Message;
import net.improved.improvedscoreboard.bukkit.command.ScoreboardSubcommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class ShowCommand implements ScoreboardSubcommand {

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length < 3) {
            Message.HELP_SHOW.send(sender);
            return false;
        }

        String name = args[1];
        Scoreboard scoreboard = ImprovedScoreboardAPI.api().getScoreboardManager().getScoreboard(name);
        if (scoreboard == null) {
            Message.SCOREBOARD_DOES_NOT_EXIST.send(sender, name);
            return false;
        }

        Player player = Bukkit.getPlayer(args[2]);
        if (player == null) {
            Message.PLAYER_DOES_NOT_EXIST.send(sender, args[2]);
            return false;
        }

        ImprovedScoreboardAPI.api().getScoreboardManager().show(player, scoreboard);
        Message.SHOW_SUCCESS.send(sender, name, player.getName());
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 2) {
            return ImprovedScoreboardAPI.api().getScoreboardManager().getScoreboards().stream().map(scoreboard -> scoreboard.getData().getName()).filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase())).toList();
        }

        if (args.length == 3) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(name -> name.toLowerCase().startsWith(args[2].toLowerCase())).toList();
        }

        return Collections.emptyList();
    }
}
