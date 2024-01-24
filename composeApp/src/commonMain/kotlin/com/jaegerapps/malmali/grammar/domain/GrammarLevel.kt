package com.jaegerapps.malmali.grammar.domain

data class GrammarLevel(
    val title: String,
    val isUnlocked: Boolean,
    val grammarList: List<GrammarPoint>
)
