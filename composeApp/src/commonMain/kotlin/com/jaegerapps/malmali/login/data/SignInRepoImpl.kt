package com.jaegerapps.malmali.login.data

import com.jaegerapps.malmali.login.domain.SignInRepo
import com.jaegerapps.malmali.login.domain.UserData
import core.domain.SettingFunctions
import core.domain.SupabaseSignInFunctions
import core.util.Resource
import io.github.jan.supabase.exceptions.RestException

class SignInRepoImpl(
    private val settings: SettingFunctions,
    private val signInFunctions: SupabaseSignInFunctions,
) : SignInRepo {
    override suspend fun createUserLocally(email: String, userId: String): Resource<Boolean> {
        return try {
            settings.createUser(email, userId)
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun createUserWithGmailExternally(userId: String): Resource<UserData> {
        val newUser = UserDTO(
            user_nickname = "",
            user_email = "",
            user_id = userId,
            user_experience = 0,
            user_icon = "",
            user_achievements = arrayOf(""),
            user_sets = arrayOf(""),
        )
        return try {
            signInFunctions.createUserGoogle(newUser)
        } catch (e: Exception) {
            println(e.message)
            Resource.Error(e)
        }
    }

    override suspend fun createUserWithEmailExternally(
        email: String,
        password: String,
    ): Resource<UserData> {
        return try {
            signInFunctions.createUserEmail(email, password)
        } catch (e: RestException) {
            Resource.Error(e)
        }
    }

    override suspend fun signInWithEmail(email: String, password: String): Resource<UserData> {
        return signInFunctions.signInUserEmail(email, password)
    }

}