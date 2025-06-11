package com.tompeas.graphql.federation.resolver

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import java.util.concurrent.CompletableFuture
import kotlin.reflect.full.findAnnotation
import com.apollographql.federation.graphqljava._Entity

class EntityResolver(
    private val resolvers: List<Entity<*>>
): DataFetcher<CompletableFuture<Any>> {

    private val entityResolverMap = mutableMapOf<String, Entity<*>>()

    init {
        resolvers.forEach { resolver ->
            val annotation = resolver::class.findAnnotation<FederationEntity>() ?: throw IllegalArgumentException("${resolver::class.simpleName} does not have @FederationEntity annotation")
            entityResolverMap[annotation.typeName] = resolver
        }
    }

    private fun entityArgs(env: DataFetchingEnvironment): List<Map<String, Any>> {
        return env.getArgument(_Entity.argumentName) ?: throw IllegalArgumentException("No entity argument named ${_Entity.argumentName}")
    }

    private fun getEntity(env: DataFetchingEnvironment): CompletableFuture<*> {
        val typeName = entityArgs(env)
        TODO()
    }
}