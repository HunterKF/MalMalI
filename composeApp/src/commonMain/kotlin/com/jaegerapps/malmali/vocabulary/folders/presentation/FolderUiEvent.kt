package com.jaegerapps.malmali.vocabulary.folders.presentation

sealed interface FolderUiEvent {
    data class OnShareClick(val setId: Int): FolderUiEvent
    data class OnEditClick(val setTitle: String, val setId: Int, val date: String): FolderUiEvent
    data class OnStudyClick(val setId: Int, val title: String, val date: String): FolderUiEvent
    data object OnNavigateToCreateClick: FolderUiEvent
    data class OnModalNavigate(val route: String): FolderUiEvent
}