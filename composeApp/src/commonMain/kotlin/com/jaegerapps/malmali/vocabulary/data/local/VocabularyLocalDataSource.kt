package com.jaegerapps.malmali.vocabulary.data.local

import com.jaegerapps.malmali.vocabulary.data.models.FlashcardEntity
import com.jaegerapps.malmali.vocabulary.data.models.SetEntity
import core.util.Resource
import kotlinx.coroutines.flow.Flow

interface VocabularyLocalDataSource {
    suspend fun createSet(setEntity: SetEntity, cards: List<FlashcardEntity>): Resource<Boolean>
    suspend fun readSingleSet(setId: Long): Resource<SetEntity>
    fun readAllSets(): Flow<List<SetEntity>>
    suspend fun updateSet(setEntity: SetEntity, cardEntityList: List<FlashcardEntity>): Resource<Boolean>
    suspend fun updateSetCards(cards: List<FlashcardEntity>): Resource<Boolean>
    suspend fun deleteSet(remoteId: Long): Resource<Boolean>
}