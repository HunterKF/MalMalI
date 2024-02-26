package com.jaegerapps.malmali.data

import com.jaegerapps.malmali.login.domain.SignInDataSource
import com.jaegerapps.malmali.login.domain.UserData
import core.util.Resource

class FakeSignInRepo: SignInDataSource {
    override suspend fun createUserLocally(email: String, userId: String): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun createUserWithGmailExternally(): Resource<UserData> {
        TODO("Not yet implemented")
    }


    override suspend fun createUserWithEmailExternally(
        email: String,
        password: String,
    ): Resource<UserData> {
        TODO("Not yet implemented")
    }

    override suspend fun signInWithEmail(email: String, password: String): Resource<UserData> {
        TODO("Not yet implemented")
    }
}