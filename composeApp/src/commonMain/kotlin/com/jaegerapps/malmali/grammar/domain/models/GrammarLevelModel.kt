package com.jaegerapps.malmali.grammar.domain.models

data class GrammarLevelModel(
    val id: Int,
    val title: String,
    val isSelected: Boolean = false,
    val isUnlocked: Boolean,
    val grammarList: List<GrammarPointModel>
)
