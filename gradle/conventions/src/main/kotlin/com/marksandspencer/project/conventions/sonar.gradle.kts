package com.marksandspencer.project.conventions

plugins {
    id("org.sonarqube")
}

subprojects {
    apply(plugin = "org.sonarqube")

    sonar {
        val buildDir = project.layout.buildDirectory
        properties {

            property("sonar.sources", "src/main/kotlin")
            property("sonar.tests", "src/test/kotlin")

            val xmlReportPaths =
                listOf(
                    buildDir.file("reports/jacoco/jacocoTestReport/jacocoTestReport.xml").get(),
                    buildDir.file("reports/jacoco/test/jacocoTestReport.xml").get(),
                )
            property("sonar.coverage.jacoco.xmlReportPaths", xmlReportPaths.joinToString(","))
        }
    }
}

sonar {
    properties {
        val projectKey = project.providers.gradleProperty("com.marksandspencer.project.sonar.projectKey").orNull
        val projectName = project.providers.gradleProperty("com.marksandspencer.project.sonar.projectName").orNull
        if (projectKey != null && projectName != null) {
            property("sonar.projectKey", projectKey)
            property("sonar.projectName", projectKey)
        }
        property("sonar.organization", "digitalinnovation")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}
