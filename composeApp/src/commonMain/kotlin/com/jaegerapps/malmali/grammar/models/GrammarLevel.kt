package com.jaegerapps.malmali.grammar.models

data class GrammarLevel(
    val id: Int,
    val title: String,
    val isSelected: Boolean = false,
    val isUnlocked: Boolean,
    val grammarList: List<GrammarPoint>
)
