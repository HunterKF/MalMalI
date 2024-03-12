package com.jaegerapps.malmali.vocabulary.presentation.create_set

import androidx.compose.ui.graphics.painter.Painter
import com.jaegerapps.malmali.common.models.IconResource
import com.jaegerapps.malmali.common.models.VocabularyCardModel

data class CreateSetUiState(
    val title: String = "",
    val vocabularyCardModels: List<VocabularyCardModel> = emptyList(),
    val showSavePopUp: Boolean = false,
    val icon: IconResource? = null,
    val tags: List<String> = emptyList(),
    val iconList: List<Painter> = emptyList(),
    val selectIcon: Boolean = false,
    val isPublic: Boolean = true,
    val error: UiError? = null,
    val mode: PopUpMode? = null,
    val isLoading: Boolean = false,
    val dateCreated: String? = null
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
