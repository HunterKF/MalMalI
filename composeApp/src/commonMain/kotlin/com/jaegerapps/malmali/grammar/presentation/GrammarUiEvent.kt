package com.jaegerapps.malmali.grammar.presentation

import com.jaegerapps.malmali.grammar.models.GrammarLevel
import com.jaegerapps.malmali.grammar.models.GrammarPoint

sealed interface GrammarUiEvent {
    data class ToggleLevelExpansion(val toggleLevel: () -> Unit): GrammarUiEvent
    data class TogglePointExpansion(val toggleLevel: () -> Unit): GrammarUiEvent
    data class SelectAllFromLevel(val level: GrammarLevel): GrammarUiEvent
    data class DeselectAllFromLevel(val level: GrammarLevel): GrammarUiEvent
    data class SelectGrammarPoint(val level: GrammarPoint): GrammarUiEvent
    data class DeselectGrammarPoint(val level: GrammarPoint): GrammarUiEvent
    data class OnNavigate(val route: String): GrammarUiEvent
    data object ToggleEditMode: GrammarUiEvent
}