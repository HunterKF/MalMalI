package com.jaegerapps.malmali.vocabulary.presentation.study_set

import com.jaegerapps.malmali.common.models.VocabularySetModel
import com.jaegerapps.malmali.common.models.VocabularyCardModel

data class StudySetUiState(
    val set: VocabularySetModel? = null,
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
