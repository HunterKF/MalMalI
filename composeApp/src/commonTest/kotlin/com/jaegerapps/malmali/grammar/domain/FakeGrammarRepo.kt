package com.jaegerapps.malmali.grammar.domain

import com.jaegerapps.malmali.grammar.domain.repo.GrammarRepo
import core.util.Resource

class FakeGrammarRepo : GrammarRepo {
    private var list = mutableListOf(1)
    override suspend fun readSelectedLevels(): Resource<List<Int>> {
        return Resource.Success(list)
    }

    override suspend fun updateSelectedLevels(levels: List<Int>): Resource<List<Int>> {
        list = levels.toMutableList()
        return Resource.Success(list)
    }
}