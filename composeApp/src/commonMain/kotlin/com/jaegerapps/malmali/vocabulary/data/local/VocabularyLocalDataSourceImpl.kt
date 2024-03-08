package com.jaegerapps.malmali.vocabulary.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.jaegerapps.malmali.composeApp.database.MalMalIDatabase
import com.jaegerapps.malmali.vocabulary.data.models.FlashcardEntity
import com.jaegerapps.malmali.vocabulary.data.models.SetEntity
import com.jaegerapps.malmali.vocabulary.domain.mapper.toSetEntity
import core.Knower
import core.Knower.e
import core.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope

class VocabularyLocalDataSourceImpl(
    database: MalMalIDatabase,
) : VocabularyLocalDataSource {
    private val queries = database.flashCardsQueries
    override suspend fun createSet(
        setEntity: SetEntity,
        cards: List<FlashcardEntity>,
    ): Resource<Boolean> {
        return try {
            queries.insertSet(
                set_id = null,
                public_id = setEntity.linked_set,
                set_title = setEntity.set_title,
                tags = setEntity.tags,
                date_created = setEntity.date_created!!,
                is_public = setEntity.is_public,
                is_author = setEntity.is_author,
                vocabulary_word = setEntity.vocabulary_word,
                vocabulary_definition = setEntity.vocabulary_definition,
                set_icon = setEntity.set_icon,

                )

            Resource.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("InsertHistorySql", "An error has occurred here. ${e.message}")
            Resource.Error(e)
        }
    }

    override suspend fun readSingleSet(setId: Long): Resource<SetEntity> {
        return try {
            val set = queries.selectOneSet(
                setId
            ).executeAsOne().toSetEntity()

            Resource.Success(set)
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("InsertHistorySql", "An error has occurred here. ${e.message}")
            Resource.Error(e)
        }
    }


    override fun readAllSets(): Flow<List<SetEntity>> {
        return queries.selectAllSets()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { hey ->
                supervisorScope {
                    hey.map {
                        it.toSetEntity()
                    }
                }
            }
    }

    override suspend fun updateSet(
        setEntity: SetEntity,
        cardEntityList: List<FlashcardEntity>,
    ): Resource<Boolean> {
        return try {
            queries.updateSet(
                id = setEntity.set_id!!,
                setTitle = setEntity.set_title,
                tags = setEntity.tags,
                isPublic = setEntity.is_public,
                words = setEntity.vocabulary_word,
                definitions = setEntity.vocabulary_definition,
            )
            Resource.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("InsertHistorySql", "An error has occurred here. ${e.message}")
            Resource.Error(e)
        }
    }

    override suspend fun updateSetCards(cards: List<FlashcardEntity>): Resource<Boolean> {
        return try {

            Resource.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("InsertHistorySql", "An error has occurred here. ${e.message}")
            Resource.Error(e)
        }
    }

    override suspend fun deleteSet(remoteId: Long): Resource<Boolean> {
        return try {
            queries.deleteSet(remoteId)
            Resource.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("InsertHistorySql", "An error has occurred here. ${e.message}")
            Resource.Error(e)
        }
    }

}