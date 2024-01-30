package com.jaegerapps.malmali.login.data

import com.jaegerapps.malmali.components.models.IconResource
import com.jaegerapps.malmali.login.domain.SignIn
import com.jaegerapps.malmali.login.domain.UserData
import com.jaegerapps.malmali.login.domain.UserEntity
import com.jaegerapps.malmali.login.domain.toUserData
import com.russhwolf.settings.Settings
import core.util.Resource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

class SignInImpl(
    private val settings: Settings,
    private val client: SupabaseClient,
) : SignIn {
    override suspend fun createUserLocally(email: String, userId: String): Resource<Boolean> {
        return try {
            settings.putString("user_email", email)
            settings.putString("user_id", userId)
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }

    }

    override suspend fun createUserWithGmailExternally(userId: String): Resource<UserData> {
        val userDTO = UserDTO(
            user_nickname = "",
            user_email = "",
            user_id = userId,
            user_experience = 1,
            user_achievements = emptyArray(),
            user_icon = "cat"
        )

        return try {
            val oldUser = client.from("users").select(columns = Columns.list("user_id")) {
                filter {
                    eq("user_id", userId)
                }
            }
            if (oldUser.data.isEmpty() || oldUser.data == "[]") {
               val user =  client.from("users").insert(userDTO) {
                    select()
                }.decodeSingle<UserEntity>()
                return Resource.Success(user.toUserData())
            } else {
                println("User already existed :D ")
                return Resource.Success(oldUser.decodeSingle<UserEntity>().toUserData())

            }

        } catch (e: Exception) {
            return Resource.Error(e)
        }

    }

    override suspend fun createUserWithEmailExternally(
        email: String,
        password: String,
    ): Resource<UserData> {
        try {
            client.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
        } catch (e: Exception) {
            return Resource.Error(e)
        }

//        client.auth.sessionStatus.collect {
//            when (it) {
//                is SessionStatus.Authenticated -> {
//                    println(it.session.user?.id)
//                    it.session.user?.let { user ->
//                        val userDTO = UserDTO(
//                            user_nickname = "",
//                            user_email = "",
//                            user_id = user.id,
//                            user_experience = 1,
//                            user_achievements = emptyArray(),
//                            user_icon = "cat"
//                        )
//                        val oldUser =
//                            client.from("users").select(columns = Columns.list("user_id")) {
//                                filter {
//                                    eq("user_id", user.id)
//                                }
//                            }
//                        if (oldUser.data.isEmpty() || oldUser.data == "[]") {
//                            try {
//                                client.from("users").insert(userDTO) {
//                                    select()
//                                }.decodeSingle<UserEntity>()
//                            } catch (e: Exception) {
//                                return Resource.Error(e)
//                            }
//
//                        } else {
//                            println("User already existed :D ")
//                        }
//                    }
//
//                }
//
//                SessionStatus.LoadingFromStorage -> println("Loading from storage")
//                SessionStatus.NetworkError -> println("Network error")
//                SessionStatus.NotAuthenticated -> println("Not authenticated")
//            }
//        }
//        val oldUser = client.from("users").select(columns = Columns.list("user_id")) {
//            filter {
//                eq("user_email", email)
//            }
//        }
//        if (oldUser.data.isEmpty() || oldUser.data == "[]") {
//            client.from("users").insert(userDTO) {
//                select()
//            }.decodeSingle<UserEntity>()
//        } else {
//            println("User already existed :D ")
//        }
        return Resource.Success(UserData(
            nickname = "test",
            email = email,
            id = "12",
            experience = 1,
            currentLevel = 1,
            icon = IconResource.resourceFromTag("cat"),
            achievements = emptyList(),
            selectedLevels = emptyList(),

        ) )
    }

}