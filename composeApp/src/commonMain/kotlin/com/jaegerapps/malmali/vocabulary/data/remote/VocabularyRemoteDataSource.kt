package com.jaegerapps.malmali.vocabulary.data.remote

import com.jaegerapps.malmali.vocabulary.data.models.VocabSetDTO
import core.util.Resource

interface VocabularyRemoteDataSource {
    suspend fun createSet(vocabSet: VocabSetDTO): Resource<VocabSetDTO>
    suspend fun readSingleSet(setId: Int): Resource<VocabSetDTO>
    suspend fun readAllSets(): Resource<List<VocabSetDTO>>
    suspend fun updateSet(vocabSet: VocabSetDTO): Resource<VocabSetDTO>
    suspend fun deleteSet(vocabSet: VocabSetDTO): Resource<Boolean>
}