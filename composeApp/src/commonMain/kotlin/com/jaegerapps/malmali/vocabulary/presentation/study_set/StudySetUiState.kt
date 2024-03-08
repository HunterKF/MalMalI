package com.jaegerapps.malmali.vocabulary.presentation.study_set

import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel
import com.jaegerapps.malmali.vocabulary.domain.models.VocabularyCardModel

data class StudySetUiState(
    val set: VocabSetModel? = null,
    val currentCard: VocabularyCardModel? = null,
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
