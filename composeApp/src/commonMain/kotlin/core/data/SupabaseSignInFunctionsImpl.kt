package core.data

import com.jaegerapps.malmali.login.data.UserDTO
import com.jaegerapps.malmali.login.domain.UserData
import com.jaegerapps.malmali.login.domain.UserEntity
import com.jaegerapps.malmali.login.domain.toUserData
import com.jaegerapps.malmali.login.domain.toUserDto
import core.domain.SupabaseSignInFunctions
import core.util.Resource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

class SupabaseSignInFunctionsImpl(private val client: SupabaseClient) : SupabaseSignInFunctions {
    override suspend fun createUserGoogle(newUser: UserDTO): Resource<UserData> {


        return try {
            val user = client.from("users").insert(newUser) {
                select()
            }.decodeSingle<UserEntity>()
            Resource.Success(user.toUserData())
        } catch (e: Exception) {
            val userCheck =
                client.from("users").select { filter { eq("user_id", newUser.user_id) } }
                    .decodeSingleOrNull<UserEntity>()
            if (userCheck == null) {
                Resource.Error(e)
            } else {
                Resource.Success(userCheck.toUserData())
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
                user_email = "",
                user_id = "",
                user_experience = 0,
                user_icon = "",
                user_achievements = arrayOf(),
                user_sets = arrayOf(),
            )
            val user = client.from("users").insert(newUser) {
                select()
            }.decodeSingle<UserEntity>()
            Resource.Success(user.toUserData())
        } catch (e: Exception) {
            return Resource.Error(e)
        }
    }

    override suspend fun updateUser(user: UserDTO): Resource<UserData> {
        return try {
            val updatedUser = client.from("users").update(user) {
                select()
            }.decodeSingle<UserEntity>()
            Resource.Success(updatedUser.toUserData())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun logOutUser(): Resource<String> {
        return try {
            client.auth.signOut()
            Resource.Success("Logged out")
        } catch (e: Exception) {
            Resource.Error(e)
        }

    }
}