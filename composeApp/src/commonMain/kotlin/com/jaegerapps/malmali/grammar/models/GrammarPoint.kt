package com.jaegerapps.malmali.grammar.models

data class GrammarPoint(
    val grammarCategory: Int,
    val grammarTitle: String,
    val grammarDef1: String,
    val grammarDef2: String? = null,
    val exampleEng1: String,
    val exampleEng2: String,
    val exampleKor1: String,
    val exampleKor2: String,
    val selected: Boolean = false
)
