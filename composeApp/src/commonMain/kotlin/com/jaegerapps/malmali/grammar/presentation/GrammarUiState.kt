package com.jaegerapps.malmali.grammar.presentation

import com.jaegerapps.malmali.grammar.domain.GrammarLevel

data class GrammarUiState(
    val levels: List<GrammarLevel> = emptyList(),
    val isEditing: Boolean = false,
    val isLoading: Boolean = false
)