import org.jreleaser.model.Active

plugins {
    id("java")
    id("java-library")
    id("maven-publish")
    id("com.gradleup.shadow") version "9.0.0-beta12"
    id("org.jreleaser") version "1.18.0"
}

group = "com.buildbound"
version = System.getenv("LIBRARY_VERSION") ?: "dev"

allprojects {
    apply(plugin = "java")
    apply(plugin = "com.gradleup.shadow")

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://oss.sonatype.org/content/groups/public/")
        maven("https://repo.codemc.io/repository/maven-releases/")
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:1.21.7-R0.1-SNAPSHOT")
        compileOnly("org.jetbrains:annotations:26.0.0")
    }

    tasks {
        shadowJar {
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
    }
}

dependencies {
    api("org.eclipse.collections:eclipse-collections-api:11.1.0")
    api("org.eclipse.collections:eclipse-collections:11.1.0")

    api("org.incendo:cloud-core:2.0.0")
    api("org.incendo:cloud-annotations:2.0.0")
    api("org.incendo:cloud-paper:2.0.0-beta.10")

    api("io.github.classgraph:classgraph:4.8.179")
    api("com.github.retrooper:packetevents-spigot:2.9.1")
}

tasks {
    withType<Javadoc> {
        (options as? StandardJavadocDocletOptions)?.apply {
            encoding = "UTF-8"

            // Custom options
            addBooleanOption("html5", true)
            addStringOption("-release", "21")
        }
    }

    shadowJar {
        archiveClassifier = ""
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("shadow") {
            from(components["java"])

            groupId = "com.buildbound"
            artifactId = "library"
            version = project.version.toString()

            pom {
                name.set(this@create.artifactId)
                description.set("The main library for all buildbound products.")
                url.set("https://github.com/buildbound/library")

                licenses {
                    license {
                        name.set("GNU General Public License v3.0")
                        url.set("https://github.com/buildbound/library/blob/main/LICENSE")
                    }
                }

                developers {
                    developer {
                        id.set("chubbyduck1")
                        email.set("Chubbyduck1@pm.me")
                    }
                }

                issueManagement {
                    system.set("GitHub")
                    url.set("https://github.com/buildbound/library/issues")
                }

                scm {
                    connection.set("scm:git:git://github.com/buildbound/library.git")
                    developerConnection.set("scm:git:git@github.com:buildbound/library.git")
                    url.set("https://github.com/buildbound/library")
                    tag.set("HEAD")
                }

                ciManagement {
                    system.set("Github Actions")
                    url.set("https://github.com/buildbound/library/actions")
                }
            }
        }
    }

    repositories {
        maven {
            url = layout.buildDirectory.dir("staging-deploy").get().asFile.toURI()
        }
    }
}

jreleaser {
    signing {
        active = Active.ALWAYS
        armored = true
    }
    release {
        github {
            token = "empty"
            skipRelease = true
        }
    }

    project {
        versionPattern = "CUSTOM"
    }

    deploy {
        maven {
            mavenCentral {
                create("sonatype") {
                    active = Active.ALWAYS
                    url = "https://central.sonatype.com/api/v1/publisher"
                    stagingRepository("build/staging-deploy")
                    username = System.getenv("SONATYPE_USERNAME")
                    password = System.getenv("SONATYPE_PASSWORD")
                }
            }
        }
    }
}