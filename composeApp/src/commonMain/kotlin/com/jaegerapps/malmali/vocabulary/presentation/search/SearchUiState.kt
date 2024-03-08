package com.jaegerapps.malmali.vocabulary.presentation.search

import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel

data class SearchUiState(
    val searchText: String = "",
    val selectedSet: VocabSetModel? = null,
    val sets: List<VocabSetModel> = emptyList(),
    val loading: Boolean = false,
    val loadingMore: Boolean = false,
    val showPopUp: Boolean = false,
    val error: String? = null,
    val startPageRange: Long = 0L,
    val endPageRange: Long = 9L,
    val endOfRange: Boolean = false
)


