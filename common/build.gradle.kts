plugins {
    id("java-library")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
}