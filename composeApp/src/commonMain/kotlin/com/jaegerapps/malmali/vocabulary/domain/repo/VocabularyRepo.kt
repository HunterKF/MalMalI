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
    suspend fun getLocalSet(setId: Int, remoteId: Int): Resource<VocabSetModel>
     fun getAllLocalSets(): Flow<List<VocabSetModel>>
    suspend fun getAllRemotePublicSets(start: Long, end: Long): Resource<List<VocabSetModel>>
    suspend fun searchPublicSets(title: String, start: Long, end: Long): Resource<List<VocabSetModel>>
    suspend fun deleteSet(setId: Int, remoteId: Int, isAuthor: Boolean): Resource<Boolean>
    suspend fun updateSet(set: VocabSetModel): Resource<Boolean>
}