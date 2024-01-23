package com.jaegerapps.malmali.vocabulary.data

import VocabularySetSourceFunctions
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.jaegerapps.malmali.composeApp.database.MalMalIDatabase
import com.jaegerapps.malmali.vocabulary.create_set.presentation.SetMode
import com.jaegerapps.malmali.vocabulary.create_set.presentation.toViewLong
import com.jaegerapps.malmali.vocabulary.domain.UiFlashcard
import com.jaegerapps.malmali.vocabulary.domain.VocabSet
import com.jaegerapps.malmali.vocabulary.mapper.toUiFlashcard
import com.jaegerapps.malmali.vocabulary.mapper.toVocabSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope

class VocabularySetSourceFunctionsImpl(database: MalMalIDatabase) : VocabularySetSourceFunctions {
    private val queries = database.flashCardsQueries

    override suspend fun addSet(
        title: String,
        size: Long,
        tags: String?,
        isPrivate: SetMode,
        id: Long?,
        dateCreated: Long
    ) {
        queries.insertSet(
            null,
            set_title = title,
            set_size = size,
            tags = tags,
            date_created = dateCreated,
            is_public = isPrivate.toViewLong()
        )
    }

    override suspend fun getSet(title: String, date: Long): VocabSet {
        return queries.selectOneSet(title, date).executeAsOne().toVocabSet()
    }

    override fun getSetAsFlow(setTitle: String, date: Long): Flow<VocabSet> {
        return queries.selectOneSet(setTitle, dateCreated =date)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { listEntity ->
                supervisorScope {
                    listEntity.map {
                        it.toVocabSet()
                    }
                        .first()
                }
            }
    }

    override fun getAllSets(): Flow<List<VocabSet>> {
        return queries.selectAllSets()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { listEntity ->
                supervisorScope {
                    listEntity.map {
                        it.toVocabSet()
                    }
                }
            }
    }


    override suspend fun deleteSet(setId: Long) {
        queries.deleteSet(setId)
    }

    override suspend fun updateSet(set: VocabSet) {
        queries.updateSet(
            setTitle = set.title, size = 0, tags = null, isPrivate = set.isPrivate.toViewLong(), id = set.setId!!
        )
    }


    override suspend fun insertCards(list: List<UiFlashcard>, linkedSetId: Long) {
        list.forEach {
            when (it.cardId) {
                null -> {
                    queries.insertFlashCard(
                        id = null,
                        word = it.word,
                        meaning = it.def,
                        memorization_level = it.level,
                        linked_set = linkedSetId
                    )
                }
                else -> {
                    queries.updateCard(
                        id = it.cardId,
                        word = it.word,
                        meaning = it.def,
                        newLevel = it.level
                    )
                }
            }

        }
    }

    override suspend fun updateCard(card: UiFlashcard) {
        queries.updateCard(card.word, card.def, card.level, card.cardId!!)
    }

    override suspend fun deleteSingleCard(card: UiFlashcard) {
        queries.deleteSingleCard(card.cardId!!)
    }

    override suspend fun deleteAllCards(setId: Long) {
        queries.deleteSetCards(setId)
    }

    override fun getAllSetCards(setId: Long): Flow<List<UiFlashcard>> {
        return queries.selectSetCards(setId)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { listEntity ->
                supervisorScope {
                    listEntity.map {
                        it.toUiFlashcard()
                    }
                }
            }
    }

}
