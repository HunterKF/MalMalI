package com.jaegerapps.malmali.common.models

import androidx.compose.runtime.Stable

data class VocabularyCardModel(
    val uiId: Int? = null,
    val word: String,
    val definition: String,
    val error: Boolean = false,
    val dbId: Long? = null
)
