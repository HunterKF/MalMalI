package com.jaegerapps.malmali.vocabulary.data.local

import com.jaegerapps.malmali.vocabulary.data.models.FlashcardEntity
import com.jaegerapps.malmali.vocabulary.data.models.SetEntity
import com.jaegerapps.malmali.vocabulary.data.models.VocabSetDTO
import core.util.Resource

interface VocabularyLocalDataSource {
    suspend fun createSet(setEntity: SetEntity, cards: List<FlashcardEntity>): Resource<Boolean>
    suspend fun readSingleSet(setId: Long): Resource<SetEntity>
    suspend fun readSingleSetCards(setId: Long): Resource<List<FlashcardEntity>>
    suspend fun readAllSets(): Resource<List<SetEntity>>
    suspend fun updateSet(setEntity: SetEntity): Resource<Boolean>
    suspend fun updateSetCards(cards: List<FlashcardEntity>): Resource<Boolean>
    suspend fun deleteSet(setEntity: SetEntity): Resource<Boolean>
}