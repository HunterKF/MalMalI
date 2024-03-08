package com.jaegerapps.malmali.data

import com.jaegerapps.malmali.components.models.IconResource
import com.jaegerapps.malmali.vocabulary.domain.repo.VocabularyRepo
import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel
import com.jaegerapps.malmali.vocabulary.domain.models.VocabularyCardModel
import core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeVocabularyRepo : VocabularyRepo {
    private val localState = MutableStateFlow(
        listOf(
            VocabSetModel(
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
            VocabSetModel(
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
            VocabSetModel(
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

    override suspend fun createSet(vocabSetModel: VocabSetModel): Resource<Boolean> {
        return Resource.Success(true)
    }

    override suspend fun insertSetLocally(vocabSetModel: VocabSetModel): Resource<Boolean> {
        return Resource.Success(true)

    }

    override suspend fun getLocalSet(setId: Int, remoteId: Int): Resource<VocabSetModel> {
        return Resource.Success(localState.value.first())
    }

    override fun getAllLocalSets(): Flow<List<VocabSetModel>> {
        return localState.asStateFlow()
    }

    override suspend fun getAllRemotePublicSets(
        start: Long,
        end: Long,
    ): Resource<List<VocabSetModel>> {
        return Resource.Success(localState.value)
    }


    override suspend fun deleteSet(setId: Int, remoteId: Int): Resource<Boolean> {
        return Resource.Success(true)
    }


    override suspend fun updateSet(set: VocabSetModel): Resource<Boolean> {
        return Resource.Success(true)
    }


}