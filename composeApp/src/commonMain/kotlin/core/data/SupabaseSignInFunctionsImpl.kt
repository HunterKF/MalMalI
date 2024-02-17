package core.data

import com.jaegerapps.malmali.login.data.UserDTO
import com.jaegerapps.malmali.login.domain.UserData
import com.jaegerapps.malmali.login.domain.UserEntity
import com.jaegerapps.malmali.login.domain.toUserData
import core.domain.SupabaseSignInFunctions
import core.util.Resource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from

class SupabaseSignInFunctionsImpl(private val client: SupabaseClient) : SupabaseSignInFunctions {
    override suspend fun createUserGoogle(newUser: UserDTO): Resource<UserData> {
        return try {
            val user = client.from("users").insert(newUser) {
                select()
            }.decodeSingle<UserEntity>()
            Resource.Success(user.toUserData())
        } catch (e: RestException) {
            /*TODO - Find a new way to check */

            val id = client.auth.currentUserOrNull()?.id
            if (!id.isNullOrEmpty()) {
                val userCheck =
                    client.from("users").select { filter { eq("user_id", id) } }
                        .decodeSingleOrNull<UserEntity>()
                if (userCheck == null) {
                    println("Error from createUserGoogle")
                    println(e.message)
                    println(e.error)
                    println(e.description)
                    Resource.Error(e)
                } else {
                    Resource.Success(userCheck.toUserData())
                }
            } else {
                Resource.Error(Throwable())
            }

        }
    }

    override suspend fun createUserEmail(
        email: String,
        password: String,
    ): Resource<UserData> {
        return try {
            client.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            val newUser = UserDTO(
                user_nickname = "",
                user_experience = 0,
                user_current_level = 1,
                user_icon = "",
                user_achievements = arrayOf(),
                user_sets = arrayOf(),
            )
            val user = client.from("users").insert(newUser) {
                select()
            }.decodeSingle<UserEntity>()
            Resource.Success(user.toUserData())
        } catch (e: RestException) {
            val userCheck =
                client.from("users").select { filter { eq("user_email", email) } }
                    .decodeSingleOrNull<UserEntity>()
            if (userCheck == null) {
                Resource.Error(e)
            } else {
                Resource.Success(userCheck.toUserData())
            }
        }
    }

    override suspend fun updateUser(user: UserDTO): Resource<UserData> {
        return try {
            val updatedUser = client.from("users").update(user) {
                select()
            }.decodeSingle<UserEntity>()
            Resource.Success(updatedUser.toUserData())
        } catch (e: RestException) {
            Resource.Error(e)
        }
    }

    override suspend fun logOutUser(): Resource<String> {
        return try {
            client.auth.signOut()
            Resource.Success("Logged out")
        } catch (e: RestException) {
            Resource.Error(e)
        }

    }

    override suspend fun signInUserEmail(email: String, password: String): Resource<UserData> {
        return try {
            client.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            val userCheck =
                client.from("users").select { filter { eq("user_email", email) } }
                    .decodeSingleOrNull<UserEntity>()
            if (userCheck != null) {
                Resource.Success(userCheck.toUserData())
            } else {
                Resource.Error(Throwable(message = "Didn't work"))
            }
        } catch (e: RestException) {

            Resource.Error(e)
        }

    }
}