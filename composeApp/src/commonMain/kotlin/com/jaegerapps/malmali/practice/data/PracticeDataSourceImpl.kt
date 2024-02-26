package com.jaegerapps.malmali.practice.data

import com.jaegerapps.malmali.composeApp.database.MalMalIDatabase
import com.jaegerapps.malmali.login.domain.UserEntity
import com.jaegerapps.malmali.practice.domain.PracticeDataSource
import com.jaegerapps.malmali.practice.mappers.toHistoryDTO
import com.jaegerapps.malmali.practice.mappers.toHistoryEntity
import com.jaegerapps.malmali.practice.models.HistoryEntity
import com.jaegerapps.malmali.practice.models.UiHistoryItem
import core.Knower
import core.Knower.d
import core.Knower.e
import core.util.Resource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.datetime.Clock

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
                grammar_level = dto.grammar_level,
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

    override suspend fun getHistorySql(): Resource<List<HistoryEntity>> {
        return try {
            val result = database.flashCardsQueries.selectHistory()
                .executeAsList()
                .map {
                    it.toHistoryEntity()
                }
            Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("getHistorySQL", "An error has occurred here. ${e.message}")
            Resource.Error(Throwable())
        }
    }

}