package com.jaegerapps.malmali.core

import androidx.compose.runtime.mutableStateOf
import com.jaegerapps.malmali.components.models.IconResource
import com.jaegerapps.malmali.login.domain.SignInDataSource
import com.jaegerapps.malmali.login.domain.UserData
import core.util.Resource
import io.github.jan.supabase.exceptions.RestException

class FakeSignInImpl(): SignInDataSource {
    private val userDataMutableState = mutableStateOf(
        UserData(
            nickname = "",
            experience = 0,
            currentLevel = 1,
            icon = IconResource.resourceFromTag("bear 1"),
        )
    )
    private var error = mutableStateOf<RestException?>(null)
    override suspend fun createUserLocally(email: String, userId: String): Resource<Boolean> {
        return if (error.value == null) {
            Resource.Success(true)
        } else {
            Resource.Error(error.value!!)
        }
    }

    override suspend fun createUserWithGmailExternally(): Resource<UserData> {
        TODO("Not yet implemented")
    }

    override suspend fun createUserWithEmailExternally(
        email: String,
        password: String,
    ): Resource<UserData> {
        return if (error.value == null) {
            Resource.Success(userDataMutableState.value)
        } else {
            Resource.Error(error.value!!)
        }
    }

    override suspend fun signInWithEmail(email: String, password: String): Resource<UserData> {
        return if (error.value == null) {
            Resource.Success(userDataMutableState.value)
        } else {
            Resource.Error(error.value!!)
        }
    }
    fun changeError(e: RestException) {
        error.value = e
    }
}