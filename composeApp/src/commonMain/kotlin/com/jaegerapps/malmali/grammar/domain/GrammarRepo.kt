package com.jaegerapps.malmali.grammar.domain

interface GrammarRepo {
    suspend fun getGrammar(): List<GrammarLevel>
}