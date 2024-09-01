import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    id("java-library")
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.6")
    api(project(":api"))
    api(project(":common"))
    api(project(":v1_21", configuration = "reobf"))
    api(project(":v1_20_6", configuration = "reobf"))
    api(project(":v1_20_4", configuration = "reobf"))
    api(project(":v1_20_2", configuration = "reobf"))
    api(project(":v1_20_1", configuration = "reobf"))
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveFileName.set("${rootProject.name}-${project.version}.jar")
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
}

bukkit {
    main = "net.improved.improvedscoreboard.bukkit.ImprovedScoreboardMain"
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    apiVersion = "1.19"
    name = rootProject.name
    softDepend = listOf("PlaceholderAPI")
}