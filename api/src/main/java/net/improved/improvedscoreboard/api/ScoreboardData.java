package net.improved.improvedscoreboard.api;

import net.improved.improvedscoreboard.api.component.AnimatedScoreboardComponent;
import net.improved.improvedscoreboard.api.component.ScoreboardComponent;
import net.improved.improvedscoreboard.api.component.StandardScoreboardComponent;
import org.bukkit.configuration.ConfigurationSection;

public class ScoreboardData {
    private final String name;
    private ScoreboardComponent title = new StandardScoreboardComponent("", 20);
    private final ScoreboardLine[] lines = new ScoreboardLine[15];
    private boolean persistent;
    private boolean dirty;

    public ScoreboardData(String name) {
        this.name = name;

        for (int i = 0; i < 15; i++) {
            lines[i] = new ScoreboardLine();
        }
    }

    public String getName() {
        return name;
    }

    public ScoreboardComponent getTitle() {
        return title;
    }

    public ScoreboardData setTitle(ScoreboardComponent title) {
        this.title = title;
        return this;
    }

    /**
     * Adds a component to the highest scoreboard index
     * @param component ScoreboardComponent to add to scoreboard
     * @return ScoreboardData
     */
    public ScoreboardData addLine(ScoreboardComponent component) {
        if (getLineCount() >= 15) {
            throw new IllegalStateException("cannot have more than 15 lines");
        }
        for (int i = 0; i < 15; i++) {
            if (lines[i].getComponent() == null) {
                lines[i].setComponent(component);
                break;
            }
        }
        setDirty(true);
        return this;
    }

    /**
     * Inserts a component at the given index
     * @param component ScoreboardComponent to add to scoreboard
     * @param index Index to insert component at
     * @return ScoreboardData
     */
    public ScoreboardData insertLine(ScoreboardComponent component, int index) {
        int lineCount = getLineCount();
        if (index < 0 || index > lineCount || lineCount >= 15) {
            throw new IllegalStateException("index must be >= 0, <= current line count, and there must be less than 15 lines");
        }
        for (int i = lineCount; i > index; i--) {
            lines[i].setComponent(lines[i - 1].getComponent());
        }
        lines[index].setComponent(component);
        setDirty(true);
        return this;
    }

    /**
     * Removes the line at the given index
     * @param index Index of line to remove
     * @return ScoreboardData
     */
    public ScoreboardData removeLine(int index) {
        if (index < 0 || index >= getLineCount()) {
            throw new IllegalStateException("line at index does not exist");
        }
        lines[index].setComponent(null);

        for (int i = index; i < 14; i++) {
            ScoreboardLine next = lines[i + 1];
            if (next.getComponent() == null) {
                break;
            }
            lines[i].setComponent(next.getComponent());
            next.setComponent(null);
        }
        setDirty(true);
        return this;
    }

    /**
     * Gets the component at an index
     * @param index Index of component to get
     * @return ScoreboardComponent at the index
     */
    public ScoreboardComponent getLine(int index) {
        if (index < 0 || index >= getLineCount()) return null;
        return lines[index].getComponent();
    }

    /**
     * Gets the amount of lines a scoreboard has
     * @return Amount of lines in the scoreboard
     */
    public int getLineCount() {
        int i = 0;
        for (ScoreboardLine line : lines) {
            if (line.getComponent() == null) {
                break;
            }
            i++;
        }
        return i;
    }

    public ScoreboardLine[] getLines() {
        return lines;
    }

    public boolean isPersistent() {
        return persistent;
    }

    public ScoreboardData setPersistent(boolean persistent) {
        this.persistent = persistent;
        return this;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public static ScoreboardData read(ConfigurationSection config) {
        ConfigurationSection title = config.getConfigurationSection("title");
        ScoreboardComponent component = new StandardScoreboardComponent("", 20);
        if (title != null) {
            if (title.isList("text")) {
                component = AnimatedScoreboardComponent.read(title);
            } else {
                component = StandardScoreboardComponent.read(title);
            }
        }

        ScoreboardData data = new ScoreboardData(config.getName()).setPersistent(true).setTitle(component);

        ConfigurationSection lines = config.getConfigurationSection("lines");
        if (lines != null) {
            for (String name : lines.getKeys(false)) {
                ConfigurationSection section = lines.getConfigurationSection(name);
                if (section != null) {
                    if (section.isList("text")) {
                        data.lines[Integer.parseInt(name)].setComponent(AnimatedScoreboardComponent.read(section));
                    } else {
                        data.lines[Integer.parseInt(name)].setComponent(StandardScoreboardComponent.read(section));
                    }
                }
            }
        }

        return data;
    }

    public void write(ConfigurationSection config) {
        ConfigurationSection title = config.getConfigurationSection("title");
        if (title == null) {
            title = config.createSection("title");
        }
        this.title.write(title);

        ConfigurationSection lines = config.getConfigurationSection("lines");
        if (lines == null) {
            lines = config.createSection("lines");
        }

        for (int i = getLineCount() - 1; i >= 0; i--) {
            ScoreboardComponent line = getLines()[i].getComponent();
            String sectionName = Integer.toString(i);
            ConfigurationSection section = lines.getConfigurationSection(sectionName);
            if (section == null) {
                section = lines.createSection(sectionName);
            }
            line.write(section);
        }
    }

    public ScoreboardData copy(String name) {
        ScoreboardData data = new ScoreboardData(name).setPersistent(true).setTitle(title.copy());
        for (int i = 0; i < getLineCount(); i++) {
            data.lines[i].setComponent(lines[i].getComponent().copy());
        }
        return data;
    }
}
