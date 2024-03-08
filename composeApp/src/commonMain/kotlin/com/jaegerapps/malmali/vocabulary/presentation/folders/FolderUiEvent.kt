package com.jaegerapps.malmali.vocabulary.presentation.folders

sealed interface FolderUiEvent {
    data class OnShareClick(val setId: Int): FolderUiEvent
    data class OnEditClick(val localId: Int, val remoteId: Int): FolderUiEvent
    data class OnStudyClick(val localId: Int,val remoteId: Int): FolderUiEvent
    data object OnNavigateToCreateClick: FolderUiEvent
    data class OnModalNavigate(val route: String): FolderUiEvent
}