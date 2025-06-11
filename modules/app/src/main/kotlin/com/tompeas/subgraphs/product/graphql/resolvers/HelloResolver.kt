package com.tompeas.subgraphs.product.graphql.resolvers

import com.tompeas.graphql.resolver.Resolver
import com.tompeas.graphql.resolver.Resolves
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import jakarta.inject.Singleton

@Singleton
@Resolves("Query", "hello")
class HelloResolver: Resolver<String> {

    override suspend fun resolve(env: DataFetchingEnvironment): String {
        val name = env.getArgument<String>("name").orEmpty().trim()
        return "Hello $name"
    }
}