package com.tompeas.subgraphs.product.health

import io.micronaut.serde.annotation.Serdeable.Serializable
import io.swagger.v3.oas.annotations.media.Schema
import java.time.Instant

@Serializable
data class HealthResponse(
    @Schema(
        description = "Indicates the service status, its computed from the service components",
        required = true,
        example = "OK",
    )
    val status: HealthStatus,
    @Schema(
        description = "Public version of the service",
        required = false,
        example = "c1b2b3b4b5b6b7b8b9b0",
    )
    val version: String? = null,
    @Schema(
        description = "Detailed health statuses of components",
        required = false,
    )
    val checks: Map<String, CheckDetail>? = null,
)

@Serializable
enum class HealthStatus {
    OK,
    FAIL,
    DEGRADED,
}

@Serializable
data class CheckDetail(
    val componentName: String,
    val componentType: ComponentType,
    val status: HealthStatus,
    val checked: Instant,
    val error: ErrorDetail? = null,
    val version: String? = null,
)

@Serializable
enum class ComponentType {
    COMPONENT,
    SERVICE,
    DEPENDENCY,
}

@Serializable
data class ErrorDetail(
    val errorType: String? = null,
    val errorCode: String? = null,
    val errorMessage: String? = null,
)
