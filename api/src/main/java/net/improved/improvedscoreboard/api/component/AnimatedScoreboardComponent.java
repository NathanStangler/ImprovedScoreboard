package net.improved.improvedscoreboard.api.component;

import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class AnimatedScoreboardComponent extends ScoreboardComponent {
    private List<String> text;
    private int current;

    /**
     * Creates an AnimatedScoreboardComponent that will not be checked for updates, e.g. placeholders
     * @param text List of text that the component should hold
     */
    public AnimatedScoreboardComponent(List<String> text) {
        this(text, DEFAULT_DELAY);
    }

    /**
     * Creates an AnimatedScoreboardComponent that will update the text
     * @param text List of text that the component should hold
     * @param delay Delay between updates
     */
    public AnimatedScoreboardComponent(List<String> text, int delay) {
        super(delay);
        this.text = text;
    }

    @Override
    public String getText() {
        return text.get(current);
    }

    public List<String> getFrames() {
        return text;
    }

    public void addText(String text) {
        this.text.add(text);
        setDirty(true);
    }

    public void setText(int frame, String text) {
        if (frame < 0 || this.text.size() < frame) {
            throw new IllegalArgumentException("frame is out of range");
        }
        this.text.set(frame, text);
        setDirty(true);
    }

    public void setText(List<String> text) {
        this.text = text;
        setDirty(true);
    }

    public int getCurrentFrame() {
        return current;
    }

    public void nextFrame() {
        current = text.size() - 1 <= current ? 0 : current + 1;
    }

    public static AnimatedScoreboardComponent read(ConfigurationSection config) {
        return new AnimatedScoreboardComponent(config.getStringList("text"), config.getInt("delay", DEFAULT_DELAY));
    }

    @Override
    public void write(ConfigurationSection config) {
        config.set("text", getText());
        config.set("delay", getDelay());
    }

    @Override
    public AnimatedScoreboardComponent copy() {
        return new AnimatedScoreboardComponent(text, getDelay());
    }
}
