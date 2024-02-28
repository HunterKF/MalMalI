package com.jaegerapps.malmali.vocabulary.create_set.presentation

import androidx.compose.ui.graphics.painter.Painter
import com.jaegerapps.malmali.components.models.IconResource
import com.jaegerapps.malmali.vocabulary.models.UiFlashcard

data class CreateSetUiState(
    val title: String = "",
    val uiFlashcards: List<UiFlashcard> = emptyList(),
    val showSavePopUp: Boolean = false,
    val icon: IconResource? = null,
    val tags: List<String> = emptyList(),
    val iconList: List<Painter> = emptyList(),
    val selectIcon: Boolean = false,
    val isPublic: Boolean = true,
    val error: UiError? = null,
    val mode: PopUpMode? = null,
    val isLoading: Boolean = false
)

enum class SetMode {
    PRIVATE,
    PUBLIC
}

enum class UiError {
    MISSING_WORD,
    UNKNOWN_ERROR,
    ADD_CARDS,
    MISSING_TITLE
}
