package net.improved.improvedscoreboard.bukkit;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

public interface Message {
    Component PREFIX = text().append(text("ImprovedScoreboard: ").color(AQUA).decorate(TextDecoration.BOLD)).build();

    Message2<String, String> HELP_BUILDER = (usage, info) -> text()
            .append(text("/improvedscoreboard ").append(text(usage)))
            .append(text(" - ").color(DARK_GRAY))
            .append(text(info).color(GRAY))
            .clickEvent(ClickEvent.suggestCommand("/improvedscoreboard " + usage))
            .hoverEvent(HoverEvent.showText(text(info).color(GRAY)))
            .build();

    Message0 HELP_HELP = () -> HELP_BUILDER.build("help", "Lists all commands");
    Message0 HELP_SAVE = () -> HELP_BUILDER.build("save", "Saves all currently registered scoreboards");
    Message0 HELP_RELOAD = () -> HELP_BUILDER.build("reload", "Reloads all scoreboards from the config");
    Message0 HELP_LIST = () -> HELP_BUILDER.build("list [page]", "Lists all registered scoreboards");
    Message0 HELP_CREATE = () -> HELP_BUILDER.build("create <name>", "Creates a new scoreboard");
    Message0 HELP_DELETE = () -> HELP_BUILDER.build("delete <name>", "Deletes a scoreboard");
    Message0 HELP_SHOW = () -> HELP_BUILDER.build("show <name> <player>", "Shows a scoreboard to a player");
    Message0 HELP_HIDE = () -> HELP_BUILDER.build("hide <player>", "Hides a players scoreboard");
    Message0 HELP_TITLE = () -> HELP_BUILDER.build("title <name> <text>", "Sets the title of a scoreboard");
    Message0 HELP_ADD = () -> HELP_BUILDER.build("add <name> <text>", "Adds a line to a scoreboard");
    Message0 HELP_INSERT = () -> HELP_BUILDER.build("insert <name> <line #> <text>", "Inserts a line into a scoreboard at a specific line number");
    Message0 HELP_SET = () -> HELP_BUILDER.build("set <name> <line #> <text>", "Sets the text of a scoreboard line");
    Message0 HELP_REMOVE = () -> HELP_BUILDER.build("remove <name> <line #>", "Removes a line from a scoreboard");
    Message0 HELP_DELAY = () -> HELP_BUILDER.build("delay <name> <title | line #> <delay>", "Sets the delay between updating text");

    Message0 HELP = () -> text().append(text("ImprovedScoreboard Help:").color(BLUE)).appendNewline()
            .append(HELP_HELP.build()).appendNewline()
            .append(HELP_SAVE.build()).appendNewline()
            .append(HELP_RELOAD.build()).appendNewline()
            .append(HELP_LIST.build()).appendNewline()
            .append(HELP_CREATE.build()).appendNewline()
            .append(HELP_DELETE.build()).appendNewline()
            .append(HELP_SHOW.build()).appendNewline()
            .append(HELP_HIDE.build()).appendNewline()
            .append(HELP_TITLE.build()).appendNewline()
            .append(HELP_ADD.build()).appendNewline()
            .append(HELP_INSERT.build()).appendNewline()
            .append(HELP_SET.build()).appendNewline()
            .append(HELP_REMOVE.build()).appendNewline()
            .append(HELP_DELAY.build()).build();

    Message0 RELOADED = () -> text("Reloaded ImprovedScoreboard plugin").color(RED);
    Message0 SAVED = () -> text("Saved all currently registered scoreboards").color(RED);
    Message1<String> SCOREBOARD_DOES_NOT_EXIST = name -> text("Scoreboard named ").append(text(name).color(AQUA)).append(text(" does not exist")).color(RED);
    Message1<String> PLAYER_DOES_NOT_EXIST = player -> text("Player ").append(text(player).color(AQUA)).append(text(" does not exist")).color(RED);
    Message1<Integer> INVALID_LINE = lines -> text("Invalid line number. Must be between 0-").append(text(lines)).color(RED);
    Message0 MAX_LINES = () -> text("Scoreboard has maximum number of lines").color(RED);
    Message0 LIST_NONE = () -> text("There are no registered scoreboards").color(RED);
    Message0 LIST_INVALID_PAGE = () -> text("Invalid page number").color(RED);
    Message2<Integer, Integer> LIST_TITLE = (page, pages) -> text("List of registered scoreboards <").append(text(page)).append(text("/")).append(text(pages)).append(text(">:")).color(BLUE);
    Message1<String> LIST_ENTRY = name -> text(" - ").append(text(name));
    Message1<String> CREATE_ALREADY_EXISTS = name -> text("Scoreboard named ").append(text(name).color(AQUA)).append(text(" already exists")).color(RED);
    Message1<String> CREATE_SUCCESS = name -> text("Scoreboard named ").append(text(name).color(AQUA)).append(text(" was created")).color(BLUE);
    Message1<String> DELETE_DOES_NOT_EXISTS = name -> text("Scoreboard named ").append(text(name).color(AQUA)).append(text(" does not exist")).color(RED);
    Message1<String> DELETE_SUCCESS = name -> text("Scoreboard named ").append(text(name).color(AQUA)).append(text(" was deleted")).color(BLUE);
    Message2<String, String> SHOW_SUCCESS = (name, player) -> text("Scoreboard named ").append(text(name).color(AQUA)).append(text(" was shown to ")).append(text(player).color(AQUA)).color(BLUE);
    Message1<String> HIDE_SUCCESS = player -> text("Scoreboard was hidden from ").append(text(player).color(AQUA)).color(BLUE);
    Message2<String, String> TITLE_SUCCESS = (name, title) -> text("Title of scoreboard ").append(text(name).color(AQUA)).append(text(" was changed to ")).append(toComponent(title)).color(BLUE);
    Message2<String, String> ADD_SUCCESS = (text, name) -> text("Line ").append(toComponent(text)).append(text(" was added to scoreboard ")).append(text(name).color(AQUA)).color(BLUE);
    Message3<String, Integer, String> INSERT_SUCCESS = (text, line, name) -> text("Line ").append(toComponent(text)).append(text(" was inserted at line ")).append(text(line).color(AQUA)).append(text(" into scoreboard ")).append(text(name).color(AQUA)).color(BLUE);
    Message3<Integer, String, String> SET_SUCCESS = (line, name, text) -> text("Line ").append(text(line).color(AQUA)).append(text(" for scoreboard ")).append(text(name).color(AQUA)).append(text(" was set to ")).append(toComponent(text)).color(BLUE);
    Message2<Integer, String> REMOVE_SUCCESS = (line, name) -> text("Line ").append(text(line).color(AQUA)).append(text(" for scoreboard ")).append(text(name).color(AQUA)).append(text(" was removed")).color(BLUE);
    Message0 DELAY_DURATION = () -> text("Invalid delay duration").color(RED);

    static Component toComponent(Player player, String text, boolean placeholder) {
        if (placeholder) {
            text = PlaceholderAPI.setPlaceholders(player, text);
        }
        return toComponent(text);
    }

    static Component toComponent(String text) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(
                LegacyComponentSerializer.legacyAmpersand().serialize(
                        MiniMessage.miniMessage().deserialize(
                                text.replaceAll("§", "&"))));
    }

    interface Message0 extends Message {
        Component build();
        default void send(Audience audience) {
            audience.sendMessage(PREFIX.append(build()));
        }
    }

    interface Message1<a> extends Message {
        Component build(a a);
        default void send(Audience audience, a a) {
            audience.sendMessage(PREFIX.append(build(a)));
        }
    }

    interface Message2<a, b> extends Message {
        Component build(a a, b b);
        default void send(Audience audience, a a, b b) {
            audience.sendMessage(PREFIX.append(build(a, b)));
        }
    }

    interface Message3<a, b, c> extends Message {
        Component build(a a, b b, c c);
        default void send(Audience audience, a a, b b, c c) {
            audience.sendMessage(PREFIX.append(build(a, b, c)));
        }
    }
}
