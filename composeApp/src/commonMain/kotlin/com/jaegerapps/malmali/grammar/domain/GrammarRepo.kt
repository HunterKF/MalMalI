package com.jaegerapps.malmali.grammar.domain

import com.jaegerapps.malmali.grammar.models.GrammarLevel

interface GrammarRepo {
    suspend fun getGrammar(): List<GrammarLevel>
}