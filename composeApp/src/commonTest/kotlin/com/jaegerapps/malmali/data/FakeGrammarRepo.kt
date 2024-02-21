package com.jaegerapps.malmali.data

import com.jaegerapps.malmali.grammar.models.GrammarLevel
import com.jaegerapps.malmali.grammar.domain.GrammarRepo

class FakeGrammarRepo: GrammarRepo {
    override suspend fun getGrammar(): List<GrammarLevel> {
        TODO("Not yet implemented")
    }
}