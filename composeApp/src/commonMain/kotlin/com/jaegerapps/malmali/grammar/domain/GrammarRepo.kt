package com.jaegerapps.malmali.grammar.domain

import com.jaegerapps.malmali.grammar.models.GrammarLevel
import core.util.Resource

interface GrammarRepo {
    suspend fun getGrammar(): Resource<List<GrammarLevel>>
}