package com.jaegerapps.malmali.practice.practice_settings.presentation

import com.jaegerapps.malmali.practice.practice_settings.domain.models.PracticeLevelModel
import com.jaegerapps.malmali.practice.practice_settings.domain.models.PracticeSetModel

sealed interface PracticeSettingsUiEvent {
    data class ToggleTeacherOn(val value: Boolean): PracticeSettingsUiEvent
    data class ToggleTranslationOn(val value: Boolean): PracticeSettingsUiEvent
    data class ToggleSet(val set: PracticeSetModel): PracticeSettingsUiEvent
    data class ToggleLevel(val level: PracticeLevelModel): PracticeSettingsUiEvent
    data object ClearMessage: PracticeSettingsUiEvent
    data object OnBackNavigate: PracticeSettingsUiEvent
}