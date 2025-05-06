plugins {
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "test-plugin"

dependencies {
    implementation(rootProject)
}

tasks {
    runServer {
        minecraftVersion("1.21.4")
    }
}
