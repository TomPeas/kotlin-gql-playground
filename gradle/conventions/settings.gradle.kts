rootProject.name = "conventions"

pluginManagement { 
    repositories {
        exclusiveContent {
            forRepository {
                mavenLocal()
            }
            filter {
                includeVersionByRegex("com.marksandspencer.*", ".+", ".+-SNAPSHOT")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        google()
        maven {
            fun getGprToken(): String =
                providers.gradleProperty("gpr.token")
                    .orElse(providers.environmentVariable("GITHUB_PACKAGES_TOKEN"))
                    .orNull
                    ?: throw IllegalStateException(
                        """
                            Missing GitHub token. Please set the token in one of the following ways:
                            - Add `gpr.token=your_token` to your `~/.gradle/gradle.properties`
                            - Set the `GITHUB_PACKAGES_TOKEN` environment variable.
                        """.trimIndent(),
                    )

            name = "DigitalInnovation"
            url = uri("https://maven.pkg.github.com/DigitalInnovation/packages")
            credentials {
                username = "" // GitHub uses an empty username for PAT authentication
                password = getGprToken()
            }
        }
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage") // Incubating as of Gradle 8.5.
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    @Suppress("UnstableApiUsage") // Incubating as of Gradle 8.5.
    repositories {
        exclusiveContent {
            forRepository {
                mavenLocal()
            }
            filter {
                includeVersionByRegex("com.marksandspencer.*", ".+", ".+-SNAPSHOT")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        google()
        maven {
            name = "DigitalInnovation"
            url = uri("https://maven.pkg.github.com/DigitalInnovation/packages")
            credentials {
                username = "" // GitHub uses an empty username for PAT authentication
                password = getGprToken()
            }
        }
    }
    versionCatalogs {
        create("libs") {
            from(files("../../gradle/libs.versions.toml"))
        }
    }
}

fun getGprToken(): String =
    providers.gradleProperty("gpr.token")
        .orElse(providers.environmentVariable("GITHUB_PACKAGES_TOKEN"))
        .orNull
        ?: throw IllegalStateException(
            """
                Missing GitHub token. Please set the token in one of the following ways:
                - Add `gpr.token=your_token` to your `~/.gradle/gradle.properties`
                - Set the `GITHUB_PACKAGES_TOKEN` environment variable.
            """.trimIndent(),
        )
