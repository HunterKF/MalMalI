package com.jaegerapps.malmali.grammar.domain

import com.jaegerapps.malmali.grammar.models.GrammarLevel
import com.jaegerapps.malmali.vocabulary.domain.models.FlashSetEntity
import core.util.Resource

interface RootComponentUseCases {
    suspend fun getGrammar(): Resource<List<GrammarLevel>>
    suspend fun getSets(name: String): Resource<List<FlashSetEntity>>
}