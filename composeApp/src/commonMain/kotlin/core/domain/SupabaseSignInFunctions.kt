package core.domain

import com.jaegerapps.malmali.login.data.UserDTO
import com.jaegerapps.malmali.login.domain.UserData
import core.util.Resource

interface SupabaseSignInFunctions {
    suspend fun createUserGoogle(newUser: UserDTO): Resource<UserData>
    suspend fun createUserEmail(email: String, password: String): Resource<UserData>
    suspend fun updateUser(user: UserDTO): Resource<UserData>
    suspend fun logOutUser(): Resource<String>
}