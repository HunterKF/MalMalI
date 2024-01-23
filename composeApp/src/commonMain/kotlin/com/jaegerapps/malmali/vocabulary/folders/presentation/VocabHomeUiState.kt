package com.jaegerapps.malmali.vocabulary.folders.presentation

import com.jaegerapps.malmali.vocabulary.domain.VocabSet

data class VocabHomeUiState(
    val setList: List<VocabSet> = emptyList(),
    val isExpanded: Boolean = false
)
