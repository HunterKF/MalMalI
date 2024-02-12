package com.jaegerapps.malmali.login.data

import androidx.compose.runtime.mutableStateOf
import com.jaegerapps.malmali.login.domain.SignInRepo
import com.jaegerapps.malmali.login.domain.UserData
import core.util.Resource

class FakeSignInRepoImpl: SignInRepo {

    private val returnError = mutableStateOf(false)


    override suspend fun createUserLocally(email: String, userId: String): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun createUserWithGmailExternally(userId: String): Resource<UserData> {
        TODO("Not yet implemented")
    }

    override suspend fun createUserWithEmailExternally(
        email: String,
        password: String,
    ): Resource<UserData> {
        TODO("Not yet implemented")
    }
}