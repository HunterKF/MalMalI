package com.jaegerapps.malmali.data

import VocabularySetSourceFunctions
import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.vocabulary.create_set.presentation.SetMode
import com.jaegerapps.malmali.vocabulary.models.UiFlashcard
import com.jaegerapps.malmali.vocabulary.models.VocabSet
import com.jaegerapps.malmali.vocabulary.exampleUiFlashcardList
import com.jaegerapps.malmali.vocabulary.exampleVocabSetList
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
            icon = MR.images.cat_icon,
            setId = -20,
            expanded = false,
            isPrivate = SetMode.PRIVATE,
            dateCreated = 10001
        )
    )

    override suspend fun addSet(
        title: String,
        size: Long,
        tags: String?,
        isPrivate: SetMode,
        id: Long?,
        dateCreated: Long
    ) {
        stateSet.update {
            it.copy(
                title = title,
                isPrivate = isPrivate
            )
        }
    }

    override suspend fun getSet(title: String, date: Long): VocabSet {
        return stateSet.value
    }

    override fun getSetAsFlow(setTitle: String, date: Long): Flow<VocabSet> {
        stateSet.update {
            it.copy(
                title = exampleVocabSetList.first {  it.title == setTitle }.title,
                setId = exampleVocabSetList.first {  it.title == setTitle }.setId,
                isPrivate = exampleVocabSetList.first {  it.title == setTitle }.isPrivate,
                dateCreated = exampleVocabSetList.first {  it.title == setTitle }.dateCreated,
                expanded = false,
            )
        }
        println("stateSet")
        println(stateSet.value)
        return stateSet
    }

    override fun getAllSets(): Flow<List<VocabSet>> {
        return vocabList
    }

    override suspend fun deleteSet(setId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun updateSet(set: VocabSet) {
        println("updating set")
        println(set)
    }

    override suspend fun insertCards(list: List<UiFlashcard>, linkedSetId: Long) {
        println("insert cards is happening")
    }

    override fun getAllSetCards(setId: Long): Flow<List<UiFlashcard>> {
         val list = (exampleUiFlashcardList.indices).map {
            exampleUiFlashcardList[it].copy(
                cardId = (it + 1).toLong()
            )
        }
        uiCardsState.update {
            it.plus(list)
        }
        println("uiCardsState.value")
        println(uiCardsState.value)
        return uiCardsState
    }

    override suspend fun updateCard(card: UiFlashcard) {
        println("We got here")
        uiCardsState.update {
            it.map { oldCard ->
                if (oldCard.uiId == card.uiId) {
                    card
                } else {
                    oldCard
                }
            }
        }
    }

    override suspend fun deleteSingleCard(card: UiFlashcard) {
        uiCardsState.update {
            it.minus(card)
        }
    }

    override suspend fun deleteAllCards(setId: Long) {
        uiCardsState.update {
            it.drop(it.size)
        }
    }
}