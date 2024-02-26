package com.jaegerapps.malmali.vocabulary.create_set.presentation

import androidx.compose.ui.graphics.painter.Painter
import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.vocabulary.models.UiFlashcard
import dev.icerock.moko.resources.ImageResource

data class CreateSetUiState(
    val title: String = "",
    val uiFlashcards: List<UiFlashcard> = emptyList(),
    val showSavePopUp: Boolean = false,
    val icon: ImageResource = MR.images.cat_icon,
    val iconList: List<Painter> = emptyList(),
    val selectIcon: Boolean = false,
    val isPrivate: SetMode = SetMode.PRIVATE,
    val error: UiError? = null,
    val mode: PopUpMode? = null
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

fun Long.toSetMode(): SetMode {
    return when (this.toInt()) {
        1 -> SetMode.PRIVATE
        2 -> SetMode.PUBLIC
        else -> SetMode.PRIVATE
    }
}

fun SetMode.toViewLong(): Long {
    println("this")
    println(this)
    return when (this) {
        SetMode.PRIVATE -> {
            1
        }

        SetMode.PUBLIC -> {
            2
        }
    }
}