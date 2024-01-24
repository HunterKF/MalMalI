package com.jaegerapps.malmali.grammar.presentation

import com.jaegerapps.malmali.grammar.domain.GrammarLevel

data class GrammarUiState(
    val levels: List<GrammarLevel> = emptyList(),
    val isLoading: Boolean = false
)