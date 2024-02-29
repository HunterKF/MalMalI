package com.jaegerapps.malmali.vocabulary.presentation.folders.presentation

import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel

data class VocabHomeUiState(
    val setList: List<VocabSetModel> = emptyList(),
    val error: String? = null,
    val isExpanded: Boolean = false,
    val loading: Boolean = false
)
