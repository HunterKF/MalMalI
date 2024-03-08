package com.jaegerapps.malmali.vocabulary.presentation.search

import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel

data class SearchUiState(
    val searchText: String = "",
    val selectedSet: VocabSetModel? = null,
    val sets: List<VocabSetModel> = emptyList(),
    val loading: Boolean = false,
    val showPopUp: Boolean = false,
    val error: String? = null,
)


