package com.jaegerapps.malmali.practice.practice_settings.domain.models

import com.jaegerapps.malmali.common.models.GrammarLevelModel

data class PracticeLevelModel(
    val levelModel: GrammarLevelModel,
    val isSelected: Boolean
)