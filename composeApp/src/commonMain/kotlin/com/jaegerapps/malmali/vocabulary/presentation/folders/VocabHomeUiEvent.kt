package com.jaegerapps.malmali.vocabulary.presentation.folders

sealed interface VocabHomeUiEvent {
    data class OnShareClick(val setId: Int): VocabHomeUiEvent
    data class OnEditClick(val localId: Int, val remoteId: Int, val isAuthor: Boolean): VocabHomeUiEvent
    data class OnStudyClick(val localId: Int,val remoteId: Int): VocabHomeUiEvent
    data object OnNavigateToCreateClick: VocabHomeUiEvent
    data object OnNavigateToSearchClick: VocabHomeUiEvent
    data class OnModalNavigate(val route: String): VocabHomeUiEvent
}