package core.domain

import com.jaegerapps.malmali.login.domain.UserData

interface SettingsDataSource {
    suspend fun updateUser(user: UserData)
    suspend fun updateUserName(name: String)
    suspend fun updateUserEmail(email: String)
    suspend fun updateUserId(id: String)
    suspend fun updateUserExperience(experience: Int)
    suspend fun updateUserCurrentLevel(currentLevel: Int)
    suspend fun updateUserIcon(icon: String)
    suspend fun updateUserAchievements(achievements: List<String>)
    suspend fun updateUserSelectedLevels(levels: List<Int>)
    suspend fun updateUserSets(sets: List<String>)
    suspend fun logoutUser()
    suspend fun createUser(email: String, id: String)
    suspend fun getUser(): UserData
    suspend fun getOnboardingBoolean(): Boolean
    suspend fun changeOnboardingBoolean()
    suspend fun saveToken(token: String)
    suspend fun getToken(): String?

}