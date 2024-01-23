package com.jaegerapps.malmali.vocabulary.study_flashcards

import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.vocabulary.create_set.presentation.SetMode
import com.jaegerapps.malmali.vocabulary.domain.UiFlashcard
import com.jaegerapps.malmali.vocabulary.domain.VocabSet

data class StudyFlashcardsUiState(
    val set: VocabSet? = null,
    val cards: List<UiFlashcard> = emptyList(),
    val currentCard: UiFlashcard? = null,
    val currentIndex: Int = -1,
    val showBack: Boolean = false,
    val isComplete: Boolean = false,
    val experienceGained: Long = 0,
    val uiError: StudyError? = null
)

enum class StudyError {
    NO_BACK
}
