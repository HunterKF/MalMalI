package com.jaegerapps.malmali.practice.domain.repo

import com.jaegerapps.malmali.practice.models.HistoryEntity
import com.jaegerapps.malmali.practice.models.UiHistoryItem
import core.util.Resource
import kotlinx.coroutines.flow.Flow

interface PracticeRepo {
    suspend fun insertHistorySql(history: UiHistoryItem): Resource<Boolean>
    suspend fun insertHistorySupabase(history: UiHistoryItem): Resource<Boolean>
    fun getHistorySql(): Flow<List<HistoryEntity>>

}