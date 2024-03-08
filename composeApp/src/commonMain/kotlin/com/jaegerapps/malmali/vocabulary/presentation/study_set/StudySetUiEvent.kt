package com.jaegerapps.malmali.vocabulary.presentation.study_set

sealed interface StudySetUiEvent {
    //Folder interactions
    data class OnFolderClick(val onClick: () -> Unit): StudySetUiEvent
    data object OnSetEditClick: StudySetUiEvent
    //Card interactions
    data object OnGotItClick: StudySetUiEvent
    data object OnDontKnowClick: StudySetUiEvent
    data object OnCardFlipClick: StudySetUiEvent
    data object OnFlipCard: StudySetUiEvent
    data object OnForward: StudySetUiEvent
    //On set complete interactions
    data object OnRepeatClick: StudySetUiEvent
    data object OnPrevious: StudySetUiEvent
    //Settings/Modal interactions
    data object OnSettingsClick: StudySetUiEvent
    data object OnCompleteClick: StudySetUiEvent

    data class OnModalClick(val openModal: () -> Unit): StudySetUiEvent
    data class OnModalNavigate(val route: String): StudySetUiEvent
}