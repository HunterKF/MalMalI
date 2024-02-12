package core.data

import com.jaegerapps.malmali.login.data.UserDTO
import com.jaegerapps.malmali.login.domain.UserData
import com.russhwolf.settings.Settings
import core.domain.SupabaseUserFunctions
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class SupabaseUserFunctionsImpl(private val client: SupabaseClient) : SupabaseUserFunctions {

    private val id = Settings().getString("id", "")
    override suspend fun updateUser(user: UserData) {
        client.from("users").update(user) {
            filter {
                eq("user_id", id)
            }
        }
    }

    override suspend fun updateUserName(name: String) {
        client.from("users").update({
            UserDTO::user_nickname setTo(name)
        }) {
            filter {
                eq("user_id", id)
            }
        }
    }

    override suspend fun updateUserEmail(email: String) {
        client.from("users").update({
            UserDTO::user_email setTo(email)
        }) {
            filter {
                eq("user_id", id)
            }
        }
    }

    override suspend fun updateUserId(id: String) {
        client.from("users").update({
            UserDTO::user_id setTo(id)
        }) {
            filter {
                eq("user_id", id)
            }
        }
    }

    override suspend fun updateUserExperience(experience: Int) {
        client.from("users").update({
            UserDTO::user_experience setTo(experience)
        }) {
            filter {
                eq("user_id", id)
            }
        }
    }


    override suspend fun updateUserIcon(icon: String) {
        client.from("users").update({
            UserDTO::user_icon setTo(icon)
        }) {
            filter {
                eq("user_id", id)
            }
        }
    }

    override suspend fun updateUserAchievements(achievements: List<String>) {
        client.from("users").update({
            UserDTO::user_achievements setTo(achievements.toTypedArray())
        }) {
            filter {
                eq("user_id", id)
            }
        }
    }



    override suspend fun updateUserSets(sets: List<String>) {
        client.from("users").update({
            UserDTO::user_sets setTo(sets.toTypedArray())
        }) {
            filter {
                eq("user_id", id)
            }
        }
    }

}