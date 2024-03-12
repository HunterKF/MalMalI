package com.jaegerapps.malmali.vocabulary.presentation.folders

import com.jaegerapps.malmali.common.models.VocabularySetModel

data class VocabHomeUiState(
    val setList: List<VocabularySetModel> = emptyList(),
    val error: String? = null,
    val isExpanded: Boolean = false,
    val loading: Boolean = false
)
