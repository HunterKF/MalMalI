package com.jaegerapps.malmali.home

import com.jaegerapps.malmali.common.models.IconResource
import dev.icerock.moko.resources.ImageResource

data class HomeUiState(
    val userName: String = "",
    val icon: IconResource = IconResource.Bear_One,
    val userExperience: Long = 0,
    val currentLevel: Int = 1,
    val loading: Boolean = false
)
