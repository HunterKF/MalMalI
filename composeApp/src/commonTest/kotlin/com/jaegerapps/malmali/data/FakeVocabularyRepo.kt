package com.jaegerapps.malmali.data

import com.jaegerapps.malmali.vocabulary.domain.repo.VocabularyRepo
import com.jaegerapps.malmali.components.models.IconResource
import com.jaegerapps.malmali.vocabulary.domain.models.VocabularyCardModel
import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel
import com.jaegerapps.malmali.vocabulary.exampleVocabSetModelLists
import core.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow

class FakeVocabularyRepo : VocabularyRepo {
    private val uiCardsState = MutableStateFlow(listOf<VocabularyCardModel>(

    ))
    private val vocabList = MutableStateFlow(exampleVocabSetModelLists)
    private val stateSet = MutableStateFlow(
        VocabSetModel(
            title = "",
            icon = IconResource.resourceFromTag("bear 1"),
            setId = -20,
            isPublic = false,
            dateCreated = ""
        )
    )

    override suspend fun addSet(vocabSetModel: VocabSetModel) {
        TODO("Not yet implemented")
    }

    override suspend fun getSet(setId: Int, setTitle: String): Resource<VocabSetModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSets(): Resource<List<VocabSetModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSet(setId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun updateSet(set: VocabSetModel) {
        TODO("Not yet implemented")
    }


}