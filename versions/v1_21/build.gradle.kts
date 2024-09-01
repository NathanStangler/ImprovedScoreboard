plugins {
    id("java-library")
    id("io.papermc.paperweight.userdev") version "1.7.1"
}

dependencies {
    paperweight.paperDevBundle("1.21-R0.1-SNAPSHOT")
    api(project(":common"))
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

    reobfJar {
        outputJar.set(layout.buildDirectory.file("libs/${rootProject.name}-${project.name}-${project.version}.jar"))
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
}