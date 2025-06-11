package com.tompeas.subgraphs.product.controllers

import com.tompeas.subgraphs.product.health.ApplicationHealth
import com.tompeas.subgraphs.product.health.HealthResponse
import com.tompeas.subgraphs.product.health.HealthStatus
import io.micronaut.context.annotation.Replaces
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

@MicronautTest(environments = ["test"])
class HealthControllerTest {
    @Inject
    lateinit var healthController: HealthController

    @Inject
    lateinit var applicationHealth: ApplicationHealth

    @Test
    fun testHealth() {
        every { applicationHealth.healthResponse } returns HealthResponse(HealthStatus.OK, "test")

        val response = healthController.health()
        assertNotNull(response)
        assertEquals(HealthStatus.OK, response.status)
        assertNotNull(response.version)
    }

    @Singleton
    @Replaces(ApplicationHealth::class)
    fun mockHealthCache(): ApplicationHealth = mockk<ApplicationHealth>()
}
