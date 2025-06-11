package com.tompeas.subgraphs.product.controllers

import com.tompeas.subgraphs.product.health.ApplicationHealth
import com.tompeas.subgraphs.product.health.HealthResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag

/**
 * Health controller
 * Implements a standard compliant health check endpoint.
 * https://github.com/DigitalInnovation/technology-standards/blob/main/docs/drafts/capabilities-endpoint.md#controllers-health-response
 */
@Controller
@Tag(name = "Health", description = "API to check service health status")
class HealthController(
    private val applicationHealth: ApplicationHealth,
) {
    @Get("/health")
    @Produces("application/health+json")
    @Operation(
        summary = "Get health status",
        description = "Returns the health status of the service",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Health status retrieved successfully",
                content = [
                    Content(
                        mediaType = "application/health+json",
                        schema = Schema(implementation = HealthResponse::class),
                    ),
                ],
            ),
        ],
    )
    fun health(): HealthResponse = applicationHealth.healthResponse
}
