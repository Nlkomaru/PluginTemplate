plugins {
    id("java")
    kotlin("jvm") version "1.9.20"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("xyz.jpenilla.run-paper") version "2.1.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    kotlin("plugin.serialization") version "1.9.0"
}

group = "dev.nikomaru"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://jitpack.io")
    maven("https://plugins.gradle.org/m2/")
    maven("https://repo.incendo.org/content/repositories/snapshots")
    maven("https://repo.codemc.io/repository/maven-public/")
}


dependencies {
    val paperVersion = "1.20.1-R0.1-SNAPSHOT"
    val mccoroutineVersion = "2.11.0"
    val lampVersion = "3.1.5"

    compileOnly("io.papermc.paper", "paper-api", paperVersion)

    library(kotlin("stdlib"))

    implementation("com.github.Revxrsal.Lamp","common",lampVersion)
    implementation("com.github.Revxrsal.Lamp","bukkit",lampVersion)

    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.7.3")
    implementation("org.jetbrains.kotlinx", "kotlinx-serialization-json", "1.5.1")

    library("com.github.shynixn.mccoroutine", "mccoroutine-bukkit-api", mccoroutineVersion)
    library("com.github.shynixn.mccoroutine", "mccoroutine-bukkit-core", mccoroutineVersion)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
        kotlinOptions.javaParameters = true
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "17"
    }
    build {
        dependsOn(shadowJar)
    }
}
tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks {
    runServer {
        minecraftVersion("1.20.2")
    }
}



bukkit {
    name = "Template" // need to change
    version = "miencraft_plugin_version"
    website = "https://github.com/Nlkomaru/NoticeTemplate"  // need to change

    main = "$group.template.Template"  // need to change

    apiVersion = "1.20"
}