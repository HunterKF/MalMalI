package com.jaegerapps.malmali.vocabulary.domain.repo

import com.jaegerapps.malmali.common.models.VocabularySetModel
import core.util.Resource
import kotlinx.coroutines.flow.Flow

interface VocabularyRepo {
    suspend fun createSet(
        vocabularySetModel: VocabularySetModel
    ): Resource<Boolean>
    suspend fun insertSetLocally(
        vocabularySetModel: VocabularySetModel
    ): Resource<Boolean>
    suspend fun getLocalSet(setId: Int, remoteId: Int): Resource<VocabularySetModel>
     fun getAllLocalSets(): Flow<List<VocabularySetModel>>
    suspend fun getAllRemotePublicSets(start: Long, end: Long): Resource<List<VocabularySetModel>>
    suspend fun searchPublicSets(title: String, start: Long, end: Long): Resource<List<VocabularySetModel>>
    suspend fun deleteSet(setId: Int, remoteId: Int, isAuthor: Boolean): Resource<Boolean>
    suspend fun updateSet(set: VocabularySetModel): Resource<Boolean>
}