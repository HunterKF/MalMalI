package com.jaegerapps.malmali.onboarding.personalization

import com.jaegerapps.malmali.common.models.IconResource

sealed interface PersonalizationUiEvents {
    data class OnNicknameChange(val value: String): PersonalizationUiEvents
    data object ToggleIconSelection: PersonalizationUiEvents
    data class OnIconChange(val iconResource: IconResource): PersonalizationUiEvents
    data object OnErrorClear: PersonalizationUiEvents
    data object OnNavigate: PersonalizationUiEvents
}