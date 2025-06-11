package com.tompeas.graphql.resolver

import graphql.schema.DataFetcher
import jakarta.inject.Singleton
import kotlin.reflect.full.findAnnotation

class ResolverWiring(
    private val resolvers: List<Resolver<*>>
) {
    private val resolverMap = mutableMapOf<String, MutableMap<String, DataFetcher<Any>>>()

    init {
        resolvers.forEach { resolver ->
            val annotation = resolver::class.findAnnotation<Resolves>() ?: throw IllegalArgumentException("${resolver::class.simpleName} does not have @Resolves annotation")
            val typeName = annotation.typeName
            val fieldName = annotation.fieldName

            @Suppress("UNCHECKED_CAST")
            resolverMap[typeName] = mutableMapOf(fieldName to resolver as DataFetcher<Any>)
        }
    }

    fun mapResolvers(dataFetchersWiring: MutableMap<String, MutableMap<String, DataFetcher<Any>>>){
        dataFetchersWiring += resolverMap
    }
}