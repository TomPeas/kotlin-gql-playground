package com.tompeas.subgraphs.product.health

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

@MicronautTest
class ApplicationHealthTest {
    @Inject
    lateinit var applicationHealth: ApplicationHealth

    @Test
    fun testStaticResponse() {
        runBlocking { applicationHealth.refresh() }

        val response = applicationHealth.healthResponse

        assertEquals(HealthStatus.OK, response.status)
        assertNotNull(response.version)
    }
}
