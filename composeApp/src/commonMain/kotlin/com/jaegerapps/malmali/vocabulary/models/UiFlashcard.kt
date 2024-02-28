package com.jaegerapps.malmali.vocabulary.models

data class UiFlashcard(
    val uiId: Int? = null,
    val word: String,
    val def: String,
    val error: Boolean
)
