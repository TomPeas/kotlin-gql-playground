import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

plugins {
    `kotlin-dsl`
    alias(libs.plugins.android.lint)
    alias(libs.plugins.spotless)
    alias(libs.plugins.marksandspencer.commonSpotless)
}

dependencies {
    implementation(gradleApi())

    implementation(libs.plugins.gradle.toolchains.foojay.asDependency())
    implementation(libs.plugins.kotlin.jvm.asDependency())
    implementation(libs.plugins.kotlin.ksp.asDependency())
    implementation(libs.plugins.marksandspencer.commonJacoco.asDependency())
    implementation(libs.plugins.marksandspencer.commonKotlin.asDependency())
    implementation(libs.plugins.marksandspencer.commonSpotless.asDependency())
    implementation(libs.plugins.marksandspencer.commonTesting.asDependency())
    implementation(libs.plugins.micronaut.catalog.asDependency())
    implementation(libs.plugins.sonar.asDependency())
    implementation(libs.plugins.spotless.asDependency())

    lintChecks(libs.androidx.gradleLint)
}

kotlin {
    explicitApi = ExplicitApiMode.Strict
    compilerOptions {
        allWarningsAsErrors = true
    }
}

lint {
    checkAllWarnings = true
    warningsAsErrors = true
    checkDependencies = true
}

fun Provider<PluginDependency>.asDependency(): Provider<String> = this.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }
