package com.jaegerapps.malmali.vocabulary.domain.repo

import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel
import core.util.Resource
import kotlinx.coroutines.flow.Flow

interface VocabularyRepo {
    suspend fun createSet(
        vocabSetModel: VocabSetModel
    ): Resource<Boolean>
    suspend fun insertSetLocally(
        vocabSetModel: VocabSetModel
    ): Resource<Boolean>
    suspend fun getLocalSet(setId: Int, setTitle: String): Resource<VocabSetModel>
     fun getAllLocalSets(): Flow<List<VocabSetModel>>
    suspend fun getAllRemotePublicSets(): Resource<List<VocabSetModel>>
    suspend fun deleteSet(setId: Int): Resource<Boolean>
    suspend fun updateSet(set: VocabSetModel): Resource<Boolean>
}