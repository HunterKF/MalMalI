package com.jaegerapps.malmali.data

import VocabularySetSourceFunctions
import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.components.models.IconResource
import com.jaegerapps.malmali.vocabulary.create_set.presentation.SetMode
import com.jaegerapps.malmali.vocabulary.models.UiFlashcard
import com.jaegerapps.malmali.vocabulary.models.VocabSet
import com.jaegerapps.malmali.vocabulary.exampleUiFlashcardList
import com.jaegerapps.malmali.vocabulary.exampleVocabSetList
import core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeVocabularySetSourceFunctions : VocabularySetSourceFunctions {
    private val uiCardsState = MutableStateFlow(listOf<UiFlashcard>(

    ))
    private val vocabList = MutableStateFlow(exampleVocabSetList)
    private val stateSet = MutableStateFlow(
        VocabSet(
            title = "",
            icon = IconResource.resourceFromTag("bear 1"),
            setId = -20,
            isPublic = false,
            dateCreated = ""
        )
    )

    override suspend fun addSet(vocabSet: VocabSet) {
        TODO("Not yet implemented")
    }

    override suspend fun getSet(setId: Int, setTitle: String): Resource<VocabSet> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSets(): Resource<List<VocabSet>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSet(setId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun updateSet(set: VocabSet) {
        TODO("Not yet implemented")
    }


}