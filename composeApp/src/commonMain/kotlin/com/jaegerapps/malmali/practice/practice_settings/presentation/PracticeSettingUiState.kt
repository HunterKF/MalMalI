package com.jaegerapps.malmali.practice.practice_settings.presentation

import com.jaegerapps.malmali.practice.practice_settings.domain.models.PracticeLevelModel
import com.jaegerapps.malmali.practice.practice_settings.domain.models.PracticeSetModel

data class PracticeSettingUiState(
    val levels: List<PracticeLevelModel> = emptyList(),
    val sets: List<PracticeSetModel> = emptyList(),
    val enableTranslation: Boolean = false,
    val enableTeacher: Boolean = false,
    val scaffoldMessage: String? = null
)
