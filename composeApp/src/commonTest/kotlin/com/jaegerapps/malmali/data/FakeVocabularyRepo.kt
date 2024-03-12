package com.jaegerapps.malmali.data

import com.jaegerapps.malmali.common.models.IconResource
import com.jaegerapps.malmali.vocabulary.domain.repo.VocabularyRepo
import com.jaegerapps.malmali.common.models.VocabularySetModel
import com.jaegerapps.malmali.common.models.VocabularyCardModel
import core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeVocabularyRepo : VocabularyRepo {
    private val localState = MutableStateFlow(
        listOf(
            VocabularySetModel(
                localId = 123,
                remoteId = 456,
                title = "Test Set 1",
                icon = IconResource.Bear_One,
                isAuthor = true,
                isPublic = true,
                tags = listOf("tag1", "tag2"),
                dateCreated = "2022-01-01",
                cards = listOf(
                    VocabularyCardModel(1, "Word1", "Definition1", false),
                    VocabularyCardModel(2, "Word2", "Definition2", false)
                )
            ),
            VocabularySetModel(
                localId = 123,
                remoteId = 456,
                title = "Test Set 2",
                icon = IconResource.Bear_One,
                isAuthor = true,
                isPublic = true,
                tags = listOf("tag1", "tag2"),
                dateCreated = "2022-01-01",
                cards = listOf(
                    VocabularyCardModel(1, "Word1", "Definition1", false),
                    VocabularyCardModel(2, "Word2", "Definition2", false)
                )
            ),
            VocabularySetModel(
                localId = 123,
                remoteId = 456,
                title = "Test Set 3",
                icon = IconResource.Bear_One,
                isAuthor = true,
                isPublic = true,
                tags = listOf("tag1", "tag2"),
                dateCreated = "2022-01-01",
                cards = listOf(
                    VocabularyCardModel(1, "Word1", "Definition1", false),
                    VocabularyCardModel(2, "Word2", "Definition2", false)
                )
            ),
        )
    )

    override suspend fun createSet(vocabularySetModel: VocabularySetModel): Resource<Boolean> {
        return Resource.Success(true)
    }

    override suspend fun insertSetLocally(vocabularySetModel: VocabularySetModel): Resource<Boolean> {
        return Resource.Success(true)

    }

    override suspend fun getLocalSet(setId: Int, remoteId: Int): Resource<VocabularySetModel> {
        return Resource.Success(localState.value.first())
    }

    override fun getAllLocalSets(): Flow<List<VocabularySetModel>> {
        return localState.asStateFlow()
    }

    override suspend fun getAllRemotePublicSets(
        start: Long,
        end: Long,
    ): Resource<List<VocabularySetModel>> {
        return Resource.Success(localState.value)
    }

    override suspend fun searchPublicSets(
        title: String,
        start: Long,
        end: Long,
    ): Resource<List<VocabularySetModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSet(
        setId: Int,
        remoteId: Int,
        isAuthor: Boolean,
    ): Resource<Boolean> {
        TODO("Not yet implemented")
    }




    override suspend fun updateSet(set: VocabularySetModel): Resource<Boolean> {
        return Resource.Success(true)
    }


}