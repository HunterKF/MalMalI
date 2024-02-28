package com.jaegerapps.malmali.practice.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.jaegerapps.malmali.composeApp.database.MalMalIDatabase
import com.jaegerapps.malmali.grammar.mapper.toGrammarLevels
import com.jaegerapps.malmali.grammar.mapper.toGrammarPoint
import com.jaegerapps.malmali.grammar.models.GrammarLevel
import com.jaegerapps.malmali.practice.domain.PracticeDataSource
import com.jaegerapps.malmali.practice.mappers.toHistoryDTO
import com.jaegerapps.malmali.practice.mappers.toHistoryEntity
import com.jaegerapps.malmali.practice.models.HistoryEntity
import com.jaegerapps.malmali.practice.models.UiHistoryItem
import com.jaegerapps.malmali.vocabulary.mapper.toVocabSet
import com.jaegerapps.malmali.vocabulary.models.VocabSet
import core.Knower
import core.Knower.e
import core.util.Resource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope

class PracticeDataSourceImpl(
    val client: SupabaseClient,
    val database: MalMalIDatabase,
) : PracticeDataSource {



    override suspend fun insertHistorySql(history: UiHistoryItem): Resource<Boolean> {

        val dto = history.toHistoryDTO()
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
            Resource.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("InsertHistorySql", "An error has occurred here. ${e.message}")
            Resource.Error(Throwable(message = e.message))

        }
    }

    override suspend fun insertHistorySupabase(history: UiHistoryItem): Resource<Boolean> {
        val dto = history.toHistoryDTO()
        return try {
            client.from("practice").insert(dto) {
                select()
            }
            Resource.Success(true)
        } catch (e: RestException) {
            e.printStackTrace()
            Knower.e("InsertHistorySupabase", "An error has occurred here. ${e.error}")
            Resource.Error(Throwable(message = e.error))

        }

    }

    override fun getHistorySql(): Flow<List<HistoryEntity>> {
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