package com.jaegerapps.malmali.onboarding.personalization

import com.jaegerapps.malmali.components.models.IconResource

sealed interface PersonalizationUiEvents {
    data class OnNicknameChange(val value: String): PersonalizationUiEvents
    data class OnIconChange(val iconResource: IconResource): PersonalizationUiEvents
    data object OnErrorClear: PersonalizationUiEvents
    data object OnNavigate: PersonalizationUiEvents
}