package com.jaegerapps.malmali.grammar.presentation

import com.jaegerapps.malmali.grammar.models.GrammarLevel

data class GrammarUiState(
    val selectedLevels: List<Int> = emptyList(),
    val grammarLevelList: List<GrammarLevel> = emptyList(),
    val isEditing: Boolean = false,
    val isLoading: Boolean = false
)