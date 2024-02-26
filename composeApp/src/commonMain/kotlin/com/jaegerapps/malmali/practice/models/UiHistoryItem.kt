package com.jaegerapps.malmali.practice.models

data class UiHistoryItem(
    val id: Int,
    val sentence: String,
    val grammar: UiPracticeGrammar,
    val vocab: UiPracticeVocab,
    val isSaved: Boolean = false
)