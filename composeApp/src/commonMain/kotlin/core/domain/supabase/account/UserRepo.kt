package core.domain.supabase.account

import com.jaegerapps.malmali.login.domain.UserData

interface UserRepo {
    suspend fun updateUser(user: UserData)
    suspend fun updateUserName(name: String)
    suspend fun updateUserId(id: String)
    suspend fun updateUserExperience(experience: Int)
    suspend fun updateUserIcon(icon: String)
    suspend fun updateUserAchievements(achievements: List<String>)
    suspend fun updateUserSets(sets: List<String>)
    suspend fun refreshAccessToken()
    suspend fun retrieveAccessToken(): String?
}