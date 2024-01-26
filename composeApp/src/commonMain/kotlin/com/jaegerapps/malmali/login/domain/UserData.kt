package com.jaegerapps.malmali.login.domain

import dev.icerock.moko.resources.ImageResource

//This is for the local ui and storage
data class UserData(
    val nickname: String,
    val email: String,
    val id: String,
    val experience: Int,
    val currentLevel: Int,
    val icon: ImageResource,
    val achievements: List<String> = emptyList(),
    val selectedLevels: List<Int> = emptyList(),
    val sets: List<String> = emptyList()
)