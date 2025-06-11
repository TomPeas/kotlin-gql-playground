package com.tompeas.graphql.resolver

/**
 * Annotation to map a class to a GraphQL type and field.
 *
 * @param typeName The GraphQL type name.
 * @param fieldName The GraphQL field name.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Resolves(
    val typeName: String,
    val fieldName: String
)