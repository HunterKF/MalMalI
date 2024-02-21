package core.data

import com.jaegerapps.malmali.login.data.UserDTO
import com.jaegerapps.malmali.login.domain.UserData
import com.russhwolf.settings.Settings
import core.domain.SupabaseUserFunctions
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from

class SupabaseUserFunctionsImpl(private val client: SupabaseClient) : SupabaseUserFunctions {

    private val id = Settings().getString("id", "")
    override suspend fun updateUser(user: UserData) {
        try {
            client.auth.currentUserOrNull()?.let {
                client.from("users").update(user) {
                    filter {
                        eq("user_id", it.id)
                    }
                }
            }
        } catch (e: Exception) {
            println(e.message)
        }

    }

    override suspend fun updateUserName(name: String) {
        try {
            client.auth.currentUserOrNull()?.let {
                client.from("users").update({
                    UserDTO::user_nickname setTo (name)
                }) {
                    filter {
                        eq("user_id", it.id)
                    }
                }
            }
        } catch (e: RestException) {
            println("Rest exception from user name update")
            println(e.message)
            println(e.error)
            println(e.description)
            println(e.cause)
        }

    }


    override suspend fun updateUserId(id: String) {
        /*TODO - delete this
           client.from("users").update({
            UserDTO::user_id setTo(id)
        }) {
            filter {
                eq("user_id", id)
            }
        }*/
    }

    override suspend fun updateUserExperience(experience: Int) {
        client.from("users").update({
            UserDTO::user_experience setTo (experience)
        }) {
            filter {
                eq("user_id", id)
            }
        }
    }


    override suspend fun updateUserIcon(icon: String) {
        try {
            client.auth.currentUserOrNull()?.let {

                client.from("users").update({
                    UserDTO::user_icon setTo (icon)
                }) {
                    filter {
                        eq("user_id", it.id)
                    }
                }
            }
        } catch (e: RestException) {
            println("Rest exception from user icon update")
            println(e.message)
            println(e.error)
            println(e.description)
            println(e.cause)
        }
    }

    override suspend fun updateUserAchievements(achievements: List<String>) {
        client.from("users").update({
            UserDTO::user_achievements setTo (achievements.toTypedArray())
        }) {
            filter {
                eq("user_id", id)
            }
        }
    }


    override suspend fun updateUserSets(sets: List<String>) {
        client.from("users").update({
            UserDTO::user_sets setTo (sets.toTypedArray())
        }) {
            filter {
                eq("user_id", id)
            }
        }
    }

    override suspend fun refreshAccessToken() {
        try {
            client.auth.currentAccessTokenOrNull()?.let {
                client.auth.refreshCurrentSession()
            }
        } catch (e: RestException) {
            println(e.message)
        }
    }

    override suspend fun retrieveAccessToken(): String? {
        return try {
            client.auth.sessionManager.loadSession()?.accessToken
        } catch (e: Exception) {
            null
        }
    }

}