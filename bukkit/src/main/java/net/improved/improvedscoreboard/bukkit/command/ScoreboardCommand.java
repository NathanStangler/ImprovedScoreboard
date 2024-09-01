package net.improved.improvedscoreboard.bukkit.command;

import net.improved.improvedscoreboard.bukkit.ImprovedScoreboardMain;
import net.improved.improvedscoreboard.bukkit.Message;
import net.improved.improvedscoreboard.bukkit.command.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ScoreboardCommand extends Command {
    private final ImprovedScoreboardMain plugin;
    private final Map<String, ScoreboardSubcommand> commands = new HashMap<>();

    public ScoreboardCommand(ImprovedScoreboardMain plugin) {
        super("improvedscoreboard", "Main command for ImprovedScoreboard", "/improvedscoreboard help", List.of("iscoreboard", "improved", "board"));
        setPermission("improvedscoreboard.admin");
        this.plugin = plugin;
        register();
    }

    private void register() {
        commands.put("list", new ListCommand());
        commands.put("create", new CreateCommand());
        commands.put("delete", new DeleteCommand());
        commands.put("show", new ShowCommand());
        commands.put("hide", new HideCommand());
        commands.put("title", new TitleCommand());
        commands.put("add", new AddCommand());
        commands.put("insert", new InsertCommand());
        commands.put("set", new SetCommand());
        commands.put("remove", new RemoveCommand());
        commands.put("delay", new DelayCommand());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!testPermission(sender)) {
            return false;
        }

        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            Message.HELP.send(sender);
            return true;
        }

        return switch (args[0].toLowerCase()) {
            case "save" -> {
                plugin.getScoreboardManager().save();
                Message.SAVED.send(sender);
                yield true;
            }
            case "reload" -> {
                plugin.getScoreboardManager().reload();
                Message.RELOADED.send(sender);
                yield true;
            }
            case "list" -> commands.get("list").execute(sender, args);
            case "create" -> commands.get("create").execute(sender, args);
            case "delete" -> commands.get("delete").execute(sender, args);
            case "show" -> commands.get("show").execute(sender, args);
            case "hide" -> commands.get("hide").execute(sender, args);
            case "title" -> commands.get("title").execute(sender, args);
            case "add" -> commands.get("add").execute(sender, args);
            case "insert" -> commands.get("insert").execute(sender, args);
            case "set" -> commands.get("set").execute(sender, args);
            case "remove" -> commands.get("remove").execute(sender, args);
            case "delay" -> commands.get("delay").execute(sender, args);
            default -> {
                Message.HELP.send(sender);
                yield false;
            }
        };
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if (args.length == 0) {
            return Collections.emptyList();
        }

        if (args.length == 1) {
            return Stream.of("help", "save", "reload", "list", "create", "delete", "show", "hide", "title", "add", "insert", "set", "remove", "delay").filter(arg -> arg.startsWith(args[0].toLowerCase())).toList();
        }

        String commandName = args[0].toLowerCase();
        if (commandName.equals("help") || commandName.equals("save") || commandName.equals("reload")) {
            return Collections.emptyList();
        }

        ScoreboardSubcommand command = commands.get(commandName);
        if (command == null) {
            return Collections.emptyList();
        }
        return command.tabComplete(sender, args);
    }
}
