package com.jaegerapps.malmali.vocabulary.study_flashcards

import com.jaegerapps.malmali.vocabulary.models.UiFlashcard
import com.jaegerapps.malmali.vocabulary.models.VocabSet
import com.jaegerapps.malmali.vocabulary.models.VocabularyCard

data class StudyFlashcardsUiState(
    val set: VocabSet? = null,
    val currentCard: VocabularyCard? = null,
    val currentIndex: Int = -1,
    val showBack: Boolean = false,
    val isComplete: Boolean = false,
    val experienceGained: Long = 0,
    val uiError: StudyError? = null,
    val loading: Boolean = false
)

enum class StudyError {
    NO_BACK
}
