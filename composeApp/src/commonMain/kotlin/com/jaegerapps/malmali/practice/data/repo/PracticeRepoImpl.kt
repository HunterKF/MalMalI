package com.jaegerapps.malmali.practice.data.repo

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.jaegerapps.malmali.composeApp.database.MalMalIDatabase
import com.jaegerapps.malmali.practice.data.local.PracticeLocalDataSource
import com.jaegerapps.malmali.practice.data.rempote.PracticeRemoteDataSource
import com.jaegerapps.malmali.practice.domain.repo.PracticeRepo
import com.jaegerapps.malmali.practice.mappers.toHistoryDTO
import com.jaegerapps.malmali.practice.mappers.toHistoryEntity
import com.jaegerapps.malmali.practice.models.HistoryEntity
import com.jaegerapps.malmali.practice.models.UiHistoryItem
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

class PracticeRepoImpl(
    private val remote: PracticeRemoteDataSource,
    private val local: PracticeLocalDataSource
) : PracticeRepo {



    override suspend fun insertHistorySql(history: UiHistoryItem): Resource<Boolean> {

        val dto = history.toHistoryDTO()
        return try {
            local.insertHistory(dto)
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
            remote.insertHistory(dto)
            Resource.Success(true)
        } catch (e: RestException) {
            e.printStackTrace()
            Knower.e("InsertHistorySupabase", "An error has occurred here. ${e.error}")
            Resource.Error(Throwable(message = e.error))

        }

    }

    override fun getHistorySql(): Flow<List<HistoryEntity>> {
        Knower.e("getHistorySql", "This was called.")
        return local.getHistory()

    }

}