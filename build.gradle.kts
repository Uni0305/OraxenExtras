plugins {
    id("idea")
    kotlin("jvm") version "2.0.21"
    id("co.uzzu.dotenv.gradle") version "4.0.0"
    id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.2.0"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "me.uni0305"
version = "0.1.1"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.oraxen.com/releases")
}

dependencies {
    compileOnly(kotlin("test"))
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly("io.th0rgal:oraxen:1.183.0")
}

kotlin {
    jvmToolchain(21)
}

bukkitPluginYaml {
    main = "me.uni0305.oraxen.OraxenExtrasPlugin"
    apiVersion = "1.20"
    author = "Uni0305"
    description = "A plugin that adds extra features to Oraxen"
    depend = listOf("Oraxen")
    libraries = listOf(
        "org.jetbrains.kotlin:kotlin-stdlib:2.0.21",
    )
}

tasks.jar {
    if (env.isPresent("JAR_DIR"))
        destinationDirectory.set(file(env.fetch("JAR_DIR")))
}

tasks.runServer {
    minecraftVersion("1.20.4")
    if (env.isPresent("SERVER_JAR"))
        serverJar(file(env.fetch("SERVER_JAR")))
}
