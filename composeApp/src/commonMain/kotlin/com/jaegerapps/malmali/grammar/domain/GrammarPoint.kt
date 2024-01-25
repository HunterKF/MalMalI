package com.jaegerapps.malmali.grammar.domain

data class GrammarPoint(
    val grammarCategory: Int,
    val grammarTitle: String,
    val grammarDef1: String,
    val grammarDef2: String? = null,
    val exampleEn1: String,
    val exampleEn2: String,
    val exampleKo1: String,
    val exampleKo2: String,
    val selected: Boolean = false
)
