package com.jaegerapps.malmali.vocabulary.presentation.search

import com.jaegerapps.malmali.common.models.VocabularySetModel

data class SearchUiState(
    val searchText: String = "",
    val selectedSet: VocabularySetModel? = null,
    val sets: List<VocabularySetModel> = emptyList(),
    val loading: Boolean = false,
    val loadingMore: Boolean = false,
    val showPopUp: Boolean = false,
    val error: String? = null,
    val startPageRange: Long = 0L,
    val endPageRange: Long = 9L,
    val endOfRange: Boolean = false
)


