package com.jaegerapps.malmali.vocabulary.domain.models

data class VocabularyCardModel(
    val uiId: Int? = null,
    val word: String,
    val definition: String,
    val error: Boolean = true,
    val dbId: Long? = null
)
