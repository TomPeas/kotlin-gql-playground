// TODO [template]: Change this to the correct group
group = "com.tompeas.subgraphs.product"

plugins {
    // TODO [template]: Remove this if rest is not your main entry point
    alias(libs.plugins.micronaut.application)
    alias(libs.plugins.micronaut.aot)
    alias(libs.plugins.gradleup.shadow)
    id("com.marksandspencer.project.module")
}

dependencies {
    ksp(mn.micronaut.http.validation)
    ksp(mn.micronaut.serde.processor)
    ksp(mn.micronaut.openapi)

    implementation(mn.micronaut.serde.jackson)
    implementation(mn.kotlin.stdlib.jdk8)
    implementation(mn.kotlinx.coroutines.core)

    implementation(mn.micronaut.http.client)

    implementation(project(":graphql"))

//    implementation(mn.micronaut.security.annotations)
//    implementation(mn.micronaut.security.jwt)
//    implementation(libs.marksandspencer.identity.auth.micronaut)

    implementation(libs.micronaut.graphql)

    implementation(libs.marksandspencer.opentelemetry.core)

    compileOnly(mn.micronaut.http.client)
    compileOnly(mn.micronaut.openapi.annotations)

    runtimeOnly(mn.snakeyaml)
    runtimeOnly(mn.logback.classic)

    testImplementation(kotlin("test"))
    testImplementation(libs.identity.jwt.test.helper)
    testImplementation(libs.mockk)
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.marksandspencer.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }
}

// TODO [template]: Make sure this is the correct main class
application {
    mainClass = "com.tompeas.subgraphs.product.Application"
}
