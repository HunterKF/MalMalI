package com.jaegerapps.malmali.login.domain

import com.jaegerapps.malmali.components.models.IconResource
import dev.icerock.moko.resources.ImageResource

//This is for the local ui and storage
data class UserData(
    val nickname: String,
    val experience: Int,
    val currentLevel: Int,
    val icon: IconResource,
    val achievements: List<String> = emptyList(),
    val selectedLevels: List<String> = emptyList(),
    val sets: List<String> = emptyList()
)