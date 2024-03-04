package com.jaegerapps.malmali.data

import com.jaegerapps.malmali.grammar.models.GrammarLevel
import com.jaegerapps.malmali.grammar.domain.RootComponentUseCases
import com.jaegerapps.malmali.vocabulary.domain.models.FlashSetEntity
import core.util.Resource

class FakeRootComponentUseCases: RootComponentUseCases {
    override suspend fun getGrammar(): Resource<List<GrammarLevel>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSets(name: String): Resource<List<FlashSetEntity>> {
        TODO("Not yet implemented")
    }
}