package com.jaegerapps.malmali.vocabulary.create_set.presentation

import androidx.compose.ui.graphics.painter.Painter

sealed interface CreateSetUiEvent {
    data object AddCard: CreateSetUiEvent
    data object AddCards: CreateSetUiEvent
    data class ChangePublicSetting(val viewSetting: Boolean): CreateSetUiEvent
    data object OpenIconSelection: CreateSetUiEvent
    data class SelectIcon(val icon: Painter): CreateSetUiEvent
    data class EditTitle(val text: String): CreateSetUiEvent
    data class EditWord(val cardId: Int, val text: String): CreateSetUiEvent
    data class EditDef(val cardId: Int, val text: String): CreateSetUiEvent
    data class DeleteWord(val cardIndex: Int): CreateSetUiEvent
    data class OnClearErrorVocab(val cardId: Int) : CreateSetUiEvent
    data class OnModalNavigate(val route: String) : CreateSetUiEvent

    data object OnClearUiError : CreateSetUiEvent
    data class TogglePopUp(val mode: PopUpMode): CreateSetUiEvent
    data object ConfirmPopUp: CreateSetUiEvent
}

enum class PopUpMode {
    DELETE,
    SAVE,
    DISMISS
}