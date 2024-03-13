package com.jaegerapps.malmali.practice.practice.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.jaegerapps.malmali.composeApp.database.MalMalIDatabase
import com.jaegerapps.malmali.practice.practice.domain.mappers.toHistoryEntity
import com.jaegerapps.malmali.practice.practice.data.models.HistoryEntity
import com.jaegerapps.malmali.vocabulary.data.models.SetEntity
import com.jaegerapps.malmali.vocabulary.domain.mapper.toSetEntity
import core.Knower
import core.Knower.d
import core.Knower.e
import core.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope

class PracticeLocalDataSourceSqlImpl(
    private val database: MalMalIDatabase,
) : PracticeLocalDataSourceSql {
    override suspend fun insertHistory(entity: HistoryEntity): Boolean {
        return try {
            database.flashCardsQueries.insertHistory(
                set_id = null,
                input_sentence = entity.input_sentence,
                grammar_point = entity.grammar_point,
                grammar_definition_1 = entity.grammar_definition_1,
                grammar_definition_2 = entity.grammar_definition_2,
                grammar_category = entity.grammar_level,
                vocabulary_word = entity.vocabulary_word,
                vocabulary_definition = entity.vocabulary_definition,
                date_created = entity.date_created,
                is_favorited = entity.is_favorited
            )
            true
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("InsertHistorySql", "An error has occurred here. ${e.message}")
            false
        }
    }

    override fun getHistory(): Flow<List<HistoryEntity>> {
        Knower.d("getHistorySql", "This was called.")
        return database.flashCardsQueries.selectHistory()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list ->
                supervisorScope {
                    list.map {
                        it.toHistoryEntity()

                    }
                }
            }
    }

    override suspend fun getSets(): Resource<List<SetEntity>> {
        return try {
            val result = database.flashCardsQueries.selectAllSets()
                .executeAsList()
                .map { it.toSetEntity() }
            Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("getSets", "An error occurred: ${e.message}")
            Resource.Error(e)
        }
    }

}