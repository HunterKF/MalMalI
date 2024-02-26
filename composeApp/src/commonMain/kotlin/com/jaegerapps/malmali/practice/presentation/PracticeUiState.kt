package com.jaegerapps.malmali.practice.presentation

import com.jaegerapps.malmali.practice.models.UiHistoryItem
import com.jaegerapps.malmali.practice.models.UiPracticeGrammar
import com.jaegerapps.malmali.practice.models.UiPracticeVocab
import com.jaegerapps.malmali.vocabulary.models.UiFlashcard
import com.jaegerapps.malmali.vocabulary.models.VocabSet

data class PracticeUiState(
    val isLoading: Boolean = false,
    val isGrammarExpanded: Boolean = false,
    val isVocabularyExpand: Boolean = false,
    val currentGrammar: UiPracticeGrammar? = null,
    val currentVocabulary: UiPracticeVocab? = null,
    val vocabularyList: List<VocabSet> = emptyList(),
    val activeFlashcards: List<UiPracticeVocab> = emptyList(),
    val grammarList: List<UiPracticeVocab> = emptyList(),
    val text: String = "",
    val history: List<UiHistoryItem> = emptyList(),
    val errorMessage: String? = null
)