package net.improved.improvedscoreboard.api.component;

import org.bukkit.configuration.ConfigurationSection;

public class StandardScoreboardComponent extends ScoreboardComponent {
    private String text;

    /**
     * Creates a StandardScoreboardComponent that will not be checked for updates, e.g. placeholders
     * @param text Text that the component should hold
     */
    public StandardScoreboardComponent(String text) {
        this(text, DEFAULT_DELAY);
    }

    /**
     * Creates a StandardScoreboardComponent that will update the text
     * @param text Text that the component should hold
     * @param delay Delay between updates
     */
    public StandardScoreboardComponent(String text, int delay) {
        super(delay);
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        setDirty(true);
    }

    public static StandardScoreboardComponent read(ConfigurationSection config) {
        return new StandardScoreboardComponent(config.getString("text"), config.getInt("delay", DEFAULT_DELAY));
    }

    @Override
    public void write(ConfigurationSection config) {
        config.set("text", getText());
        config.set("delay", getDelay());
    }

    @Override
    public StandardScoreboardComponent copy() {
        return new StandardScoreboardComponent(text, getDelay());
    }
}
