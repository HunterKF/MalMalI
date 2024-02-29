package com.jaegerapps.malmali.vocabulary.domain.repo

import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel
import core.util.Resource

interface VocabularyRepo {
    suspend fun addSet(
        vocabSetModel: VocabSetModel,
        username: String
    )
    suspend fun getSet(setId: Int, setTitle: String): Resource<VocabSetModel>
    suspend fun getAllSets(): Resource<List<VocabSetModel>>
    suspend fun deleteSet(setId: Int)
    suspend fun updateSet(set: VocabSetModel)
}