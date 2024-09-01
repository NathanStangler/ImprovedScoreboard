plugins {
    id("java-library")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

allprojects {
    group = "net.improved.improvedscoreboard"
    version = "1.0"
    description = "Improved scoreboard plugin for minecraft servers"

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}