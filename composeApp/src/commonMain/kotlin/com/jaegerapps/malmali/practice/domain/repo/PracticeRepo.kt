package com.jaegerapps.malmali.practice.domain.repo

import com.jaegerapps.malmali.practice.data.models.HistoryEntity
import com.jaegerapps.malmali.practice.domain.models.HistoryItemModel
import core.util.Resource
import kotlinx.coroutines.flow.Flow

interface PracticeRepo {
    suspend fun readSelectedLevels(): Resource<List<Int>>
    suspend fun updateSelectedLevels(levels: List<Int>): Resource<List<Int>>
    suspend fun readSelectedVocab(): Resource<List<PracticeVocabularyModel>>
    suspend fun selectSet(set)

    suspend fun insertHistorySql(history: HistoryItemModel): Resource<Boolean>
    suspend fun insertHistorySupabase(history: HistoryItemModel): Resource<Boolean>
    fun getHistorySql(): Flow<List<HistoryEntity>>

}