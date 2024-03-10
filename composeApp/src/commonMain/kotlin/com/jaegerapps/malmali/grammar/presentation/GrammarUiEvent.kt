package com.jaegerapps.malmali.grammar.presentation

import com.jaegerapps.malmali.grammar.models.GrammarLevel
import com.jaegerapps.malmali.grammar.models.GrammarPoint

sealed interface GrammarUiEvent {
    data class ToggleLevelSelection(val level: GrammarLevel): GrammarUiEvent
    data class OnNavigate(val route: String): GrammarUiEvent
    data object ToggleEditMode: GrammarUiEvent
}