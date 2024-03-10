package com.jaegerapps.malmali.vocabulary.data.repo

import androidx.compose.runtime.mutableStateOf
import com.jaegerapps.malmali.components.models.IconResource
import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel
import com.jaegerapps.malmali.vocabulary.domain.models.VocabularyCardModel
import com.jaegerapps.malmali.vocabulary.domain.repo.VocabularyRepo
import core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeVocabularyRepo: VocabularyRepo {

    private val  localSet = mutableStateOf(VocabSetModel(
        localId = 1,
        remoteId = 16,
        title = "Test 101",
        icon = IconResource.Bear_One,
        isAuthor = true,
        isPublic = false,
        tags = emptyList(),
        dateCreated = null,
        cards = listOf(
            VocabularyCardModel(uiId = null, word = "Word1", definition = "Definition1"),
            VocabularyCardModel(uiId = null, word = "Word2", definition = "Definition2")
        )
    ))
    private val localFlow = MutableStateFlow(listOf(localSet.value))
    override suspend fun createSet(vocabSetModel: VocabSetModel): Resource<Boolean> {
        return Resource.Success(true)
    }

    override suspend fun insertSetLocally(vocabSetModel: VocabSetModel): Resource<Boolean> {
        return Resource.Success(true)
    }

    override suspend fun getLocalSet(setId: Int, remoteId: Int): Resource<VocabSetModel> {
        return if (setId == 1) {
            Resource.Success(localSet.value)
        } else {
            Resource.Error(Throwable(message = "Test Error"))
        }
    }

    override fun getAllLocalSets(): Flow<List<VocabSetModel>> {
        return localFlow
    }

    override suspend fun getAllRemotePublicSets(
        start: Long,
        end: Long,
    ): Resource<List<VocabSetModel>> {
        return Resource.Success(localFlow.value.slice(start.toInt()..end.toInt()))
    }

    override suspend fun searchPublicSets(
        title: String,
        start: Long,
        end: Long,
    ): Resource<List<VocabSetModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSet(
        setId: Int,
        remoteId: Int,
        isAuthor: Boolean,
    ): Resource<Boolean> {
        TODO("Not yet implemented")
    }


    override suspend fun updateSet(set: VocabSetModel): Resource<Boolean> {
        TODO("Not yet implemented")
    }
}