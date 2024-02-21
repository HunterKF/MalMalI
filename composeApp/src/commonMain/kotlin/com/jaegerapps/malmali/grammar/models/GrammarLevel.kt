package com.jaegerapps.malmali.grammar.models

data class GrammarLevel(
    val title: String,
    val isUnlocked: Boolean,
    val grammarList: List<GrammarPoint>
)
