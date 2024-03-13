package com.jaegerapps.malmali.practice.practice.presentation

import com.jaegerapps.malmali.common.models.GrammarLevelModel
import com.jaegerapps.malmali.common.models.GrammarPointModel
import com.jaegerapps.malmali.practice.practice.domain.models.HistoryModel
import com.jaegerapps.malmali.common.models.VocabularySetModel
import com.jaegerapps.malmali.common.models.VocabularyCardModel

data class PracticeUiState(
    val isLoading: Boolean = false,
    val isGrammarExpanded: Boolean = false,
    val isVocabularyExpand: Boolean = false,
    val currentGrammar: GrammarPointModel? = null,
    val currentVocabulary: VocabularyCardModel? = null,
    val setModelList: List<VocabularySetModel> = emptyList(),
    val selectedSet: List<VocabularySetModel> = emptyList(),
    val selectedLevel: List<GrammarLevelModel> = emptyList(),
    val text: String = "",
    val history: List<HistoryModel> = emptyList(),
    val errorMessage: String? = null
)