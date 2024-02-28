package com.jaegerapps.malmali.practice.models

data class UiPracticeGrammar(
    val grammar: String,
    val definition1: String,
    val definition2: String?,
    val level: String
)

data class UiPracticeGrammarLevel(
    val title: String,
    val selected: Boolean,
    val grammar: List<UiPracticeGrammar>
)
