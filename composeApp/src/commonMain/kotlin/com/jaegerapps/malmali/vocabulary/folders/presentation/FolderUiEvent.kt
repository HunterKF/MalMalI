package com.jaegerapps.malmali.vocabulary.folders.presentation

sealed interface FolderUiEvent {
    data class OnShareClick(val setId: Long): FolderUiEvent
    data class OnEditClick(val setTitle: String, val setId: Long, val date: Long): FolderUiEvent
    data class OnStudyClick(val setId: Long, val title: String, val date: Long): FolderUiEvent
    data object OnNavigateToCreateClick: FolderUiEvent
    data class OnModalNavigate(val route: String): FolderUiEvent
    data object GetAllCards: FolderUiEvent
}