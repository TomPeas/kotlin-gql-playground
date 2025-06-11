package com.tompeas.graphql.resolver

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

interface Resolver<T>: DataFetcher<CompletableFuture<T>> {

    suspend fun resolve(env: DataFetchingEnvironment): T

    val scope: CoroutineScope
        get() = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun get(env: DataFetchingEnvironment) = scope.future {
        resolve(env)
    }
}