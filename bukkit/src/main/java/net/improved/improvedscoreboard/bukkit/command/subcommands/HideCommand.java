package net.improved.improvedscoreboard.bukkit.command.subcommands;

import net.improved.improvedscoreboard.api.ImprovedScoreboardAPI;
import net.improved.improvedscoreboard.bukkit.Message;
import net.improved.improvedscoreboard.bukkit.command.ScoreboardSubcommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class HideCommand implements ScoreboardSubcommand {
    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length < 2) {
            Message.HELP_HIDE.send(sender);
            return false;
        }

        Player player = Bukkit.getPlayer(args[1]);
        if (player == null) {
            Message.PLAYER_DOES_NOT_EXIST.send(sender, args[1]);
            return false;
        }

        ImprovedScoreboardAPI.api().getScoreboardManager().hide(player);
        Message.HIDE_SUCCESS.send(sender, player.getName());
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 2) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase())).toList();
        }

        return Collections.emptyList();
    }
}
