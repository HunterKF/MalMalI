package com.jaegerapps.malmali.practice.data.repo

import com.jaegerapps.malmali.practice.data.local.PracticeLocalDataSource
import com.jaegerapps.malmali.practice.data.rempote.PracticeRemoteDataSource
import com.jaegerapps.malmali.practice.domain.repo.PracticeRepo
import com.jaegerapps.malmali.practice.domain.mappers.toHistoryDTO
import com.jaegerapps.malmali.practice.data.models.HistoryEntity
import com.jaegerapps.malmali.practice.domain.models.HistoryItemModel
import core.Knower
import core.Knower.e
import core.util.Resource
import io.github.jan.supabase.exceptions.RestException
import kotlinx.coroutines.flow.Flow

class PracticeRepoImpl(
    private val remote: PracticeRemoteDataSource,
    private val local: PracticeLocalDataSource
) : PracticeRepo {



    override suspend fun insertHistorySql(history: HistoryItemModel): Resource<Boolean> {

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

    override suspend fun insertHistorySupabase(history: HistoryItemModel): Resource<Boolean> {
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