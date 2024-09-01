package net.improved.improvedscoreboard.api.component;

import org.bukkit.configuration.ConfigurationSection;

public abstract class ScoreboardComponent {
    protected static final int DEFAULT_DELAY = -1;
    private int delay;
    private boolean dirty;

    public ScoreboardComponent() {
        this(DEFAULT_DELAY);
    }

    public ScoreboardComponent(int delay) {
        this.delay = delay;
    }

    public abstract String getText();

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public abstract void write(ConfigurationSection config);

    public abstract ScoreboardComponent copy();
}
