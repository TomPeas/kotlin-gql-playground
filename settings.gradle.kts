// TODO [template]: Change this to the correct project name
rootProject.name = "product"

pluginManagement {
    includeBuild("gradle/conventions")
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
        maven {
            name = "DigitalInnovation"
            url = uri("https://maven.pkg.github.com/DigitalInnovation/packages")
            credentials {
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
                username = "" // GitHub uses an empty username for PAT authentication
                password = getGprToken()
            }
        }

    }
}

plugins {
    id("com.marksandspencer.project.settings")
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
        maven {
            name = "DigitalInnovation"
            url = uri("https://maven.pkg.github.com/DigitalInnovation/packages")
            credentials {
                username = "" // GitHub uses an empty username for PAT authentication
                password = getGprToken()
            }
        }
    }
}

fun getGprToken(): String =
    providers
        .gradleProperty("gpr.token")
        .orElse(providers.environmentVariable("GITHUB_PACKAGES_TOKEN"))
        .orNull
        ?: throw IllegalStateException(
            """
                Missing GitHub token. Please set the token in one of the following ways:
                - Add `gpr.token=your_token` to your `~/.gradle/gradle.properties`
                - Set the `GITHUB_PACKAGES_TOKEN` environment variable.
            """.trimIndent(),
        )

includeModule(":app")
includeModule(":graphql")

fun includeModule(path: String) {
    include(path)
    val module = project(path)
    module.projectDir = file("modules").resolve(module.projectDir.relativeTo(settings.rootDir))
}