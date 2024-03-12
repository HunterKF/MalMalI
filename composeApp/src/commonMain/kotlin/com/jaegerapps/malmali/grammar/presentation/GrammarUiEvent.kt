package com.jaegerapps.malmali.grammar.presentation

import com.jaegerapps.malmali.common.models.GrammarLevelModel

sealed interface GrammarUiEvent {
    data class ToggleLevelSelection(val level: GrammarLevelModel): GrammarUiEvent
    data class OnNavigate(val route: String): GrammarUiEvent
    data object ToggleEditMode: GrammarUiEvent
    data object ClearError: GrammarUiEvent
}