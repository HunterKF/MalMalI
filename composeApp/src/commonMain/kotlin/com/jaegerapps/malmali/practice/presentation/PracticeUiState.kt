package com.jaegerapps.malmali.practice.presentation

import com.jaegerapps.malmali.practice.models.UiHistoryItem
import com.jaegerapps.malmali.practice.models.UiPracticeGrammar
import com.jaegerapps.malmali.practice.models.UiPracticeVocab

data class PracticeUiState(
    val isLoading: Boolean = false,
    val isGrammarExpanded: Boolean = false,
    val isVocabularyExpand: Boolean = false,
    val vocabulary: UiPracticeVocab? = null,
    val grammar: UiPracticeGrammar? = null,
    val text: String = "",
    val history: List<UiHistoryItem> = emptyList()
)