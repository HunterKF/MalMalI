package com.jaegerapps.malmali.grammar.presentation

import com.jaegerapps.malmali.common.models.GrammarLevelModel

data class GrammarUiState(
    val selectedLevels: List<Int> = emptyList(),
    val grammarLevelModelList: List<GrammarLevelModel> = emptyList(),
    val isEditing: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)