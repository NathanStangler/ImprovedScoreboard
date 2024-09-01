# ImprovedScoreboard

Scoreboard plugin for Paper Minecraft servers. Supports Minecraft versions 1.20.1, 1.20.2, 1.20.4, 1.20.6, and 1.21.

## Features

- Animated scoreboard lines and title.
- World dependent scoreboards.
- MiniMessage text formatting.
- Legacy ampersand text formatting.
- PlaceholderAPI text replacement.

## Gradle API

Run `./gradlew publishToMavenLocal` to publish the api to the local maven repository.

```kts
repositories {
    mavenLocal()
}

dependencies {
    implementation("net.improved.improvedscoreboard:api:1.0")
}
```

## Usage

```java
public class Plugin {
    private Scoreboard scoreboard;
    
    public void create() {
        ScoreboardManager manager = ImprovedScoreboardAPI.api().getScoreboardManager();

        ScoreboardComponent title = new AnimatedScoreboardComponent(List.of("<red>Title", "<blue>Title"), 20);
        ScoreboardComponent line = new StandardScoreboardComponent("Player: %player_name%", 20);

        ScoreboardData data = new ScoreboardData("name").setTitle(title).addLine(line);

        this.scoreboard = manager.create(data);
    }
    
    public void show(Player player) {
        manager.show(player, scoreboard);
    }
}
```