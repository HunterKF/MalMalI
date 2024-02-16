package com.jaegerapps.malmali.data

import androidx.compose.runtime.mutableStateOf
import com.jaegerapps.malmali.components.models.IconResource
import com.jaegerapps.malmali.login.domain.UserData
import core.domain.SettingFunctions

class FakeSettingFunctions : SettingFunctions {
    private var onboardingState = mutableStateOf(false)
    private var userState = mutableStateOf(
        UserData(
            nickname = "",
            email = "",
            experience = 1,
            currentLevel = 1,
            icon = IconResource.resourceFromTag("bear 1")
        )
    )

    override suspend fun updateUser(user: UserData) {
        userState.value = user
    }

    override suspend fun updateUserName(name: String) {
        userState.value = userState.value.copy(
            nickname = name
        )
    }

    override suspend fun updateUserEmail(email: String) {
        userState.value = userState.value.copy(
            email = email
        )
    }

    override suspend fun updateUserId(id: String) {
        userState.value = userState.value.copy(
        )
    }

    override suspend fun updateUserExperience(experience: Int) {
        userState.value = userState.value.copy(
            experience = experience
        )
    }

    override suspend fun updateUserCurrentLevel(currentLevel: Int) {
        userState.value = userState.value.copy(
            currentLevel = currentLevel
        )
    }

    override suspend fun updateUserIcon(icon: String) {
        userState.value = userState.value.copy(
            icon = IconResource.resourceFromTag(icon)
        )
    }

    override suspend fun updateUserAchievements(achievements: List<String>) {
        userState.value = userState.value.copy(
            achievements = achievements
        )
    }

    override suspend fun updateUserSelectedLevels(levels: List<Int>) {
        userState.value = userState.value.copy(
            selectedLevels = levels
        )
    }

    override suspend fun updateUserSets(sets: List<String>) {
        userState.value = userState.value.copy(
            sets = sets
        )
    }

    override suspend fun logoutUser() {
        userState.value = userState.value.copy(
            nickname = ""
        )
    }

    override suspend fun createUser(email: String, id: String) {
        userState.value = userState.value.copy(
            email = email,
        )
    }

    override fun getUser(): UserData {
        return userState.value
    }

    override fun getOnboardingBoolean(): Boolean {
        return onboardingState.value
    }

    override suspend fun changeOnboardingBoolean() {
        onboardingState.value = !onboardingState.value
    }
}