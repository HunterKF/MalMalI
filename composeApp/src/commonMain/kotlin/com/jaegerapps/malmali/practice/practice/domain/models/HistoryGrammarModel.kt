package com.jaegerapps.malmali.practice.practice.domain.models

data class HistoryGrammarModel(
    val grammarCategory: Int,
    val grammarTitle: String,
    val grammarDef1: String,
    val grammarDef2: String? = null,
)
