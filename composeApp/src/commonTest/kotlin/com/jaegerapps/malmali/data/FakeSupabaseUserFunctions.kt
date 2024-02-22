package com.jaegerapps.malmali.data

import com.jaegerapps.malmali.login.domain.UserData
import core.domain.SupabaseUserFunctions

class FakeSupabaseUserFunctions: SupabaseUserFunctions {
    override suspend fun updateUser(user: UserData) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserName(name: String) {
        TODO("Not yet implemented")
    }


    override suspend fun updateUserId(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserExperience(experience: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserIcon(icon: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserAchievements(achievements: List<String>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserSets(sets: List<String>) {
        TODO("Not yet implemented")
    }

    override suspend fun refreshAccessToken() {
        TODO("Not yet implemented")
    }

    override suspend fun retrieveAccessToken(): String? {
        TODO("Not yet implemented")
    }
}