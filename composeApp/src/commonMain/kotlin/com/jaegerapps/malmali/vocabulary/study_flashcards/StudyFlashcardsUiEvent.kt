package com.jaegerapps.malmali.vocabulary.study_flashcards

sealed interface StudyFlashcardsUiEvent {
    //Folder interactions
    data class OnFolderClick(val onClick: () -> Unit): StudyFlashcardsUiEvent
    data object OnSetShareClick: StudyFlashcardsUiEvent
    data object OnSetEditClick: StudyFlashcardsUiEvent
    //Card interactions
    data class OnGotItClick(val id: Long): StudyFlashcardsUiEvent
    data class OnDontKnowClick(val id: Long): StudyFlashcardsUiEvent
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