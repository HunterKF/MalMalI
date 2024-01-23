package com.jaegerapps.malmali.vocabulary.create_set.presentation

import androidx.compose.ui.graphics.painter.Painter

sealed interface CreateSetUiEvent {
    data object AddCard: CreateSetUiEvent
    data object AddCards: CreateSetUiEvent
    data class ChangePublicSetting(val viewSetting: SetMode): CreateSetUiEvent
    data object OpenIconSelection: CreateSetUiEvent
    data class SelectIcon(val icon: Painter): CreateSetUiEvent
    data class EditTitle(val text: String): CreateSetUiEvent
    data class EditWord(val cardId: Long, val text: String): CreateSetUiEvent
    data class EditDef(val cardId: Long, val text: String): CreateSetUiEvent
    data class DeleteWord(val cardId: Long): CreateSetUiEvent
    data class OnClearErrorVocab(val cardId: Long) : CreateSetUiEvent
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