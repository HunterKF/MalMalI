package com.jaegerapps.malmali.data

import com.jaegerapps.malmali.vocabulary.domain.repo.VocabularyRepo
import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel
import core.util.Resource
import kotlinx.coroutines.flow.Flow

class FakeVocabularyRepo : VocabularyRepo {
    override suspend fun createSet(vocabSetModel: VocabSetModel): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun insertSetLocally(vocabSetModel: VocabSetModel): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getLocalSet(setId: Int, remoteId: Int): Resource<VocabSetModel> {
        TODO("Not yet implemented")
    }

    override fun getAllLocalSets(): Flow<List<VocabSetModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllRemotePublicSets(): Resource<List<VocabSetModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSet(setId: Int, remoteId: Int): Resource<Boolean> {
        TODO("Not yet implemented")
    }


    override suspend fun updateSet(set: VocabSetModel): Resource<Boolean> {
        TODO("Not yet implemented")
    }


}