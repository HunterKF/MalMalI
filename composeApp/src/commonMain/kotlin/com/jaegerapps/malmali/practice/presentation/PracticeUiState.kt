package com.jaegerapps.malmali.practice.presentation

import com.jaegerapps.malmali.practice.models.UiHistoryItem
import com.jaegerapps.malmali.practice.models.UiPracticeGrammar
import com.jaegerapps.malmali.practice.models.UiPracticeGrammarLevel
import com.jaegerapps.malmali.practice.models.UiPracticeVocab
import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel

data class PracticeUiState(
    val isLoading: Boolean = false,
    val isGrammarExpanded: Boolean = false,
    val isVocabularyExpand: Boolean = false,
    val currentGrammar: UiPracticeGrammar? = null,
    val currentVocabulary: UiPracticeVocab? = null,
    val vocabularyList: List<VocabSetModel> = emptyList(),
    val activeFlashcards: List<UiPracticeVocab> = emptyList(),
    val grammarList: List<UiPracticeGrammarLevel> = emptyList(),
    val text: String = "",
    val history: List<UiHistoryItem> = emptyList(),
    val errorMessage: String? = null
)