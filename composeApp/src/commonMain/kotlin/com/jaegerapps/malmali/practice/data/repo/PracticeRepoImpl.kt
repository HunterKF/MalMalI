package com.jaegerapps.malmali.practice.data.repo

import com.jaegerapps.malmali.common.models.VocabularySetModel
import com.jaegerapps.malmali.practice.data.local.PracticeLocalDataSourceSettings
import com.jaegerapps.malmali.practice.data.local.PracticeLocalDataSourceSql
import com.jaegerapps.malmali.practice.data.rempote.PracticeRemoteDataSource
import com.jaegerapps.malmali.practice.domain.repo.PracticeRepo
import com.jaegerapps.malmali.practice.domain.mappers.toHistoryDTO
import com.jaegerapps.malmali.practice.data.models.HistoryEntity
import com.jaegerapps.malmali.practice.domain.mappers.toHistoryEntity
import com.jaegerapps.malmali.practice.domain.models.HistoryModel
import com.jaegerapps.malmali.vocabulary.domain.mapper.toVocabSet
import com.jaegerapps.malmali.vocabulary.domain.mapper.toVocabSetModel
import core.Knower
import core.Knower.e
import core.Knower.t
import core.util.Resource
import io.github.jan.supabase.exceptions.RestException
import kotlinx.coroutines.flow.Flow

class PracticeRepoImpl(
    private val remote: PracticeRemoteDataSource,
    private val localSql: PracticeLocalDataSourceSql,
    private val localSettings: PracticeLocalDataSourceSettings,
) : PracticeRepo {
    override suspend fun readSelectedLevels(): Resource<List<Int>> {
        return localSettings.readSelectedSets()
    }

    override suspend fun updateSelectedLevels(levels: List<Int>): Resource<List<Int>> {
        return localSettings.readSelectedSets()
    }

    override suspend fun readSelectedSetIds(): Resource<List<Int>> {
        return localSettings.readSelectedSets()
    }

    override suspend fun updateSelectedSets(list: List<Int>): Resource<List<Int>> {
        return localSettings.updateSelectedSets(list)
    }

    override suspend fun readVocabSets(): Resource<List<VocabularySetModel>> {
        return when (val result = localSql.getSets()) {
            is Resource.Success -> {
                if (result.data != null) {
                    Resource.Success(result.data.map { it.toVocabSet() })
                } else {
                    Resource.Error(result.throwable!!)
                }
            }

            is Resource.Error -> {
                result.throwable?.printStackTrace()
                Knower.e("readVocabSets", "An error has occurred: ${result.throwable?.message}")
                Resource.Error(result.throwable ?: Throwable())
            }
        }
    }


    override suspend fun insertHistorySql(history: HistoryModel): Resource<Boolean> {

        val historyEntity = history.toHistoryEntity()
        return try {
            localSql.insertHistory(historyEntity)
            Resource.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("InsertHistorySql", "An error has occurred here. ${e.message}")
            Resource.Error(Throwable(message = e.message))
        }
    }

    override suspend fun insertHistorySupabase(history: HistoryModel): Resource<Boolean> {
        val dto = history.toHistoryDTO()
        return try {
            remote.insertHistory(dto)
            Resource.Success(true)
        } catch (e: RestException) {
            e.printStackTrace()
            Knower.t("InsertHistorySupabase", "An error has occurred here. ${e.error}")
            Resource.Error(Throwable(message = e.error))

        }

    }

    override suspend fun getDefaultSet(): Resource<VocabularySetModel> {
        return when (val result = remote.getDefaultSet()) {
            is Resource.Error -> Resource.Error(result.throwable ?: Throwable())
            is Resource.Success -> {
                if (result.data != null) {
                    Resource.Success(result.data.toVocabSetModel(false))
                } else {
                    Resource.Error(result.throwable ?: Throwable())
                }
            }
        }
    }

    override fun getHistorySql(): Flow<List<HistoryEntity>> {
        Knower.t("getHistorySql", "This was called.")
        return localSql.getHistory()

    }

}