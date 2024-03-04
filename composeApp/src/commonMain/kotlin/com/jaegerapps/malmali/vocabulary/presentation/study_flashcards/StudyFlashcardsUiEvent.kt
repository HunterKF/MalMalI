package com.jaegerapps.malmali.vocabulary.presentation.study_flashcards

sealed interface StudyFlashcardsUiEvent {
    //Folder interactions
    data class OnFolderClick(val onClick: () -> Unit): StudyFlashcardsUiEvent
    data object OnSetEditClick: StudyFlashcardsUiEvent
    //Card interactions
    data object OnGotItClick: StudyFlashcardsUiEvent
    data object OnDontKnowClick: StudyFlashcardsUiEvent
    data object OnCardFlipClick: StudyFlashcardsUiEvent
    data object OnFlipCard: StudyFlashcardsUiEvent
    data object OnForward: StudyFlashcardsUiEvent
    //On set complete interactions
    data object OnRepeatClick: StudyFlashcardsUiEvent
    data object OnPrevious: StudyFlashcardsUiEvent
    //Settings/Modal interactions
    data object OnSettingsClick: StudyFlashcardsUiEvent
    data object OnCompleteClick: StudyFlashcardsUiEvent

    data class OnModalClick(val openModal: () -> Unit): StudyFlashcardsUiEvent
    data class OnModalNavigate(val route: String): StudyFlashcardsUiEvent
}