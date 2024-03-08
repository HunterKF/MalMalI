package com.jaegerapps.malmali.vocabulary.data.remote

import com.jaegerapps.malmali.vocabulary.data.models.VocabSetDTO
import com.jaegerapps.malmali.vocabulary.data.models.VocabSetDTOWithoutData
import core.util.Resource

interface VocabularyRemoteDataSource {
    suspend fun createSet(vocabSet: VocabSetDTOWithoutData): Resource<VocabSetDTO>
    suspend fun readSingleSet(setId: Int): Resource<VocabSetDTO>
    suspend fun readAllSets(start: Long, end: Long): Resource<List<VocabSetDTO>>
    suspend fun updateSet(vocabSet: VocabSetDTO): Resource<VocabSetDTO>
    suspend fun deleteSet(remoteId: Int): Resource<Boolean>
}