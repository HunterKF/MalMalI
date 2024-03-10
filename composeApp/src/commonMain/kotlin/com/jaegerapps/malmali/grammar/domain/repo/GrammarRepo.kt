package com.jaegerapps.malmali.grammar.domain.repo

import core.util.Resource

interface GrammarRepo {
    suspend fun readSelectedLevels(): Resource<List<Int>>
    suspend fun updateSelectedLevels(levels: List<Int>): Resource<List<Int>>
}