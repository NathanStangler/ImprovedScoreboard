package net.improved.improvedscoreboard.api;

import net.improved.improvedscoreboard.api.component.ScoreboardComponent;

public class ScoreboardLine {
    private final String id = Long.toHexString(System.nanoTime());
    private ScoreboardComponent component;
    private boolean created;

    protected ScoreboardLine() {}

    public String getID() {
        return id;
    }

    public ScoreboardComponent getComponent() {
        return component;
    }

    public void setComponent(ScoreboardComponent component) {
        this.component = component;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }
}
