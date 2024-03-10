package com.jaegerapps.malmali

import com.jaegerapps.malmali.grammar.domain.models.GrammarLevelModel
import com.jaegerapps.malmali.vocabulary.domain.models.FlashSetEntity
import core.util.Resource

interface RootComponentUseCases {
    suspend fun getGrammar(): Resource<List<GrammarLevelModel>>
    suspend fun getSets(name: String): Resource<List<FlashSetEntity>>
}