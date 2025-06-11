package com.tompeas.subgraphs.product.graphql

import com.tompeas.graphql.resolver.Resolver
import com.tompeas.graphql.resolver.ResolverWiring
import com.tompeas.graphql.resolver.Resolves
import com.tompeas.subgraphs.product.graphql.resolvers.HelloResolver
import graphql.GraphQL
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.TypeDefinitionRegistry
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.core.io.ResourceResolver
import jakarta.inject.Singleton
import java.io.BufferedReader
import java.io.InputStreamReader

@Factory
class GraphQLFactory {

    @Bean
    @Singleton
    fun graphQL(resourceResolver: ResourceResolver, resolvers: List<Resolver<*>>): GraphQL {

        val resolverWiring = ResolverWiring(resolvers)
        val schemaParser = SchemaParser()
        val schemaGenerator = SchemaGenerator()

        val typeRegistry = TypeDefinitionRegistry()
        typeRegistry.merge(schemaParser.parse(BufferedReader(InputStreamReader(resourceResolver.getResourceAsStream("classpath:schema.graphqls").get()))))

        val runtimeWiring = RuntimeWiring.newRuntimeWiring()
            .build()

        resolverWiring.mapResolvers(runtimeWiring.dataFetchers)

        val graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring)

        return GraphQL.newGraphQL(graphQLSchema).build()
    }
}