package com.jaegerapps.malmali.vocabulary.domain

data class UiFlashcard(
    val uiId: Long? = null,
    val cardId: Long? = null,
    val word: String,
    val def: String,
    val level: Long,
    val error: Boolean
)
