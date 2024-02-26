package com.jaegerapps.malmali.practice.presentation

sealed interface PracticeUiEvent {
    data class OnNavigate(val route: String): PracticeUiEvent
    data object ToggleVocabDropDown: PracticeUiEvent
    data object ToggleGrammarDropDown: PracticeUiEvent
    data class OnValueChanged(val value: String): PracticeUiEvent
    data object SavePractice: PracticeUiEvent

}