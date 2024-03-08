package com.jaegerapps.malmali.vocabulary.presentation.search

import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel

sealed interface SearchUiEvent {
    data class SelectSet(val set: VocabSetModel): SearchUiEvent
    data object SaveSet: SearchUiEvent
    data object ClearUiMessage: SearchUiEvent
    data object LoadMore: SearchUiEvent
    data class OnSearchTextValueChange(val value: String): SearchUiEvent
    data object SearchByName: SearchUiEvent
}