package com.jaegerapps.malmali.practice.practice_settings.domain.models

import com.jaegerapps.malmali.common.models.VocabularySetModel

data class PracticeSetModel(
    val set: VocabularySetModel,
    val isSelected: Boolean
)