package com.jaegerapps.malmali.onboarding.personalization

import com.jaegerapps.malmali.components.models.IconResource

data class PersonalizationUiState(
    val error: String? = null,
    val nickname: String = "",
    val selectedIcon: IconResource? = null,
    val selectIconPopUp: Boolean = false,
)

