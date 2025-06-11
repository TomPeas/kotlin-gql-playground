package com.tompeas.subgraphs.product.helpers

import com.mns.identity.jwt.test.helper.creation.TokenGeneratorCreator
import com.mns.identity.jwt.test.helper.domain.Environment
import com.mns.identity.jwt.test.helper.domain.Store
import kotlinx.coroutines.runBlocking

private val tokenGeneratorCreator = TokenGeneratorCreator()

fun getGuestToken(): String {
    // Getting a guest token - for the UK store (default) in the PROD environment
    val tokenGenerator = tokenGeneratorCreator.createGuestUserTokenGeneratorCreator(Environment.DEV)
    val guestToken = runBlocking { tokenGenerator.generateToken() }

    // Guest token ready to be used
    return guestToken
}

fun getKnownUserToken(): String {
    val testUserName = System.getenv("TEST_JWT_USER_DEV")
    val testPassword = System.getenv("TEST_JWT_PASSWORD_DEV")
    val tokenGenerator =
        tokenGeneratorCreator.createUserTokenGeneratorCreator(
            testUserName,
            testPassword,
            Environment.DEV,
            Store.UK,
        )

    val accessToken = runBlocking { tokenGenerator.generateToken() }

    // User token ready to be used
    return accessToken
}
