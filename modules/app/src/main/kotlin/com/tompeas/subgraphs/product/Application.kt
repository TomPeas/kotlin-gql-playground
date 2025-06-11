package com.tompeas.subgraphs.product

import io.micronaut.runtime.Micronaut.run
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
    info =
        Info(
            title = "\${api.title}",
            version = "\${micronaut.application.version}",
            description = "\${api.description}",
        ),
)
class Application {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            run(*args)
        }
    }
}
