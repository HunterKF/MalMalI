package core.domain

import com.jaegerapps.malmali.login.domain.UserData

interface SupabaseUserFunctions {
    suspend fun updateUser(user: UserData)
    suspend fun updateUserName(name: String)
    suspend fun updateUserEmail(email: String)
    suspend fun updateUserId(id: String)
    suspend fun updateUserExperience(experience: Int)
    suspend fun updateUserIcon(icon: String)
    suspend fun updateUserAchievements(achievements: List<String>)
    suspend fun updateUserSets(sets: List<String>)
}