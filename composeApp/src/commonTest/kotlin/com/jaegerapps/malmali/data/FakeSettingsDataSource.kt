package com.jaegerapps.malmali.data

import androidx.compose.runtime.mutableStateOf
import com.jaegerapps.malmali.common.models.IconResource
import com.jaegerapps.malmali.login.domain.UserData
import core.domain.SettingsDataSource

class FakeSettingsDataSource : SettingsDataSource {
    private var onboardingState = mutableStateOf(false)
    private var userState = mutableStateOf(
        UserData(
            nickname = "",
            experience = 1,
            currentLevel = 1,
            icon = IconResource.resourceFromTag("bear 1")
        )
    )

    override suspend fun updateUser(user: UserData) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserName(name: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserEmail(email: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserId(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserExperience(experience: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserCurrentLevel(currentLevel: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserIcon(icon: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserAchievements(achievements: List<String>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserSelectedLevels(levels: List<Int>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserSets(sets: List<String>) {
        TODO("Not yet implemented")
    }

    override suspend fun logoutUser() {
        TODO("Not yet implemented")
    }

    override suspend fun createUser(email: String, id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(): UserData {
        return UserData(
            nickname = "Test",
            experience = 1,
            currentLevel = 1,
            icon = IconResource.Bear_One,
            achievements = listOf("award1", "award2"),
            selectedLevels = listOf("1")
        )
    }

    override suspend fun getOnboardingBoolean(): Boolean {
        return false
    }

    override suspend fun changeOnboardingBoolean() {
        TODO("Not yet implemented")
    }

    override suspend fun saveToken(token: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getToken(): String? {
        return "test token"
    }


}