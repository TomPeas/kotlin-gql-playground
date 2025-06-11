group = "com.tompeas.graphql"

plugins {
    id("com.marksandspencer.project.module")
}

dependencies {
    implementation(libs.micronaut.graphql)
    implementation(libs.apollographql.federationJava)
    implementation(mn.kotlinx.coroutines.core)
    implementation(libs.kotlin.reflect)
}