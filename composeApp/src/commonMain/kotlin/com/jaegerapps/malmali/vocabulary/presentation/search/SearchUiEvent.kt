package com.jaegerapps.malmali.vocabulary.presentation.search

import com.jaegerapps.malmali.common.models.VocabularySetModel

sealed interface SearchUiEvent {
    data class SelectSet(val set: VocabularySetModel): SearchUiEvent
    data object SaveSet: SearchUiEvent
    data object TogglePopUp: SearchUiEvent
    data object ClearUiMessage: SearchUiEvent
    data object LoadMore: SearchUiEvent
    data class OnSearchTextValueChange(val value: String): SearchUiEvent
    data object SearchByTitle: SearchUiEvent
}