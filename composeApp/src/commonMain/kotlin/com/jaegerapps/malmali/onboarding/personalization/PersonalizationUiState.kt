package com.jaegerapps.malmali.onboarding.personalization

import com.jaegerapps.malmali.common.models.IconResource

data class PersonalizationUiState(
    val error: String? = null,
    val nickname: String = "",
    val selectedIcon: IconResource = IconResource.Bear_One,
    val selectIconPopUp: Boolean = false,
)

