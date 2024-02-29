package com.jaegerapps.malmali.data

import com.jaegerapps.malmali.login.data.UserDTO
import com.jaegerapps.malmali.login.domain.UserData
import core.domain.supabase.signin.SignInRepo
import core.util.Resource

class FakeSupabaseSignInFunctions: SignInRepo {
    override suspend fun createUserGoogle(newUser: UserDTO): Resource<UserData> {
        TODO("Not yet implemented")
    }

    override suspend fun createUserEmail(email: String, password: String): Resource<UserData> {
        TODO("Not yet implemented")
    }

    override suspend fun signInUserEmail(email: String, password: String): Resource<UserData> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: UserDTO): Resource<UserData> {
        TODO("Not yet implemented")
    }

    override suspend fun logOutUser(): Resource<String> {
        TODO("Not yet implemented")
    }
}