package com.tompeas.subgraphs.product.health

import io.micronaut.context.annotation.Value
import jakarta.annotation.PreDestroy
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicReference

interface ComponentHealthCheck {
    val componentName: String
    val componentType: ComponentType

    suspend fun check(): CheckDetail

    fun stop()
}

@Singleton
class ApplicationHealth(
    private val componentHealthChecks: List<ComponentHealthCheck>,
    @Value("\${health.refresh.interval.millis}")
    private val refreshIntervalMillis: Long,
    @Value("\${micronaut.application.version}")
    private val version: String,
) {
    private var cachedHealthResponse: AtomicReference<HealthResponse> =
        AtomicReference(
            HealthResponse(
                HealthStatus.FAIL,
                "unknown",
            ),
        )
    private val coroutineScope = CoroutineScope(Dispatchers.Default + Job())

    init {
        coroutineScope.launch {
            while (true) {
                refresh()
                delay(refreshIntervalMillis)
            }
        }
    }

    var healthResponse: HealthResponse
        get() = cachedHealthResponse.get()
        private set(value) {
            cachedHealthResponse.set(value)
        }

    suspend fun refresh() {
        val checks =
            componentHealthChecks
                .map { healthCheck ->
                    coroutineScope.async { healthCheck.componentName to healthCheck.check() }
                }.awaitAll()
                .toMap()

        val status =
            if (checks.values.all { it.status == HealthStatus.OK }) {
                HealthStatus.OK
            } else if (checks.values.any { it.status == HealthStatus.FAIL }) {
                HealthStatus.FAIL
            } else {
                HealthStatus.DEGRADED
            }
        val newHealthResponse =
            HealthResponse(
                status,
                version,
                checks,
            )
        this.healthResponse = newHealthResponse
    }

    @PreDestroy
    fun stopRefresh() {
        coroutineScope.coroutineContext[Job]?.cancel()
        componentHealthChecks.forEach { it.stop() }
    }
}
