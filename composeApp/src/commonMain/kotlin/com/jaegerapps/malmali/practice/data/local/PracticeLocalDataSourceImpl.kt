package com.jaegerapps.malmali.practice.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.jaegerapps.malmali.composeApp.database.MalMalIDatabase
import com.jaegerapps.malmali.practice.domain.mappers.toHistoryEntity
import com.jaegerapps.malmali.practice.data.models.HistoryDTO
import com.jaegerapps.malmali.practice.data.models.HistoryEntity
import core.Knower
import core.Knower.e
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope

class PracticeLocalDataSourceImpl(
    private val database: MalMalIDatabase
): PracticeLocalDataSource {
    override suspend fun insertHistory(dto: HistoryDTO): Boolean {
        return try {
            database.flashCardsQueries.insertHistory(
                set_id = null,
                input_sentence = dto.input_sentence,
                grammar_point = dto.grammar_point,
                grammar_definition_1 = dto.grammar_definition_1,
                grammar_definition_2 = dto.grammar_definition_2,
                grammar_category = dto.grammar_level,
                vocabulary_word = dto.vocabulary_word,
                vocabulary_definition = dto.vocabulary_definition,
                date_created = dto.date_created,
                is_favorited = dto.is_favorited
            )
            true
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("InsertHistorySql", "An error has occurred here. ${e.message}")
            false
        }
    }

    override fun getHistory(): Flow<List<HistoryEntity>> {
        Knower.e("getHistorySql", "This was called.")
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

}