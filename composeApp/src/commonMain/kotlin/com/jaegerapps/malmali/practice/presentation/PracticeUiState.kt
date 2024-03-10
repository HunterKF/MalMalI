package com.jaegerapps.malmali.practice.presentation

import com.jaegerapps.malmali.practice.domain.models.HistoryItemModel
import com.jaegerapps.malmali.practice.domain.models.PracticeGrammarModel
import com.jaegerapps.malmali.practice.domain.models.PracticeGrammarLevelModel
import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel

data class PracticeUiState(
    val isLoading: Boolean = false,
    val isGrammarExpanded: Boolean = false,
    val isVocabularyExpand: Boolean = false,
    val currentGrammar: PracticeGrammarModel? = null,
    val currentVocabulary: PracticeVocabularyModel? = null,
    val vocabularyList: List<VocabSetModel> = emptyList(),
    val activeFlashcards: List<PracticeVocabularyModel> = emptyList(),
    val grammarList: List<PracticeGrammarLevelModel> = emptyList(),
    val text: String = "",
    val history: List<HistoryItemModel> = emptyList(),
    val errorMessage: String? = null
)