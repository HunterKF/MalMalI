package com.jaegerapps.malmali.vocabulary.folders.presentation

import com.jaegerapps.malmali.vocabulary.models.VocabSet

data class VocabHomeUiState(
    val setList: List<VocabSet> = emptyList(),
    val error: String? = null,
    val isExpanded: Boolean = false,
    val loading: Boolean = false
)
