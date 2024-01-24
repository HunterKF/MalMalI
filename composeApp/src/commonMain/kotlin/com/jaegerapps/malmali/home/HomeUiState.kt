package com.jaegerapps.malmali.home

import dev.icerock.moko.resources.ImageResource

data class HomeUiState(
    val userName: String = "",
    val icon: ImageResource? = null,
    val userExperience: Long = 0,
    val currentLevel: Int = 1
)
