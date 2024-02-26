package com.jaegerapps.malmali.practice.domain

import com.jaegerapps.malmali.practice.models.HistoryEntity
import com.jaegerapps.malmali.practice.models.UiHistoryItem
import core.util.Resource

interface PracticeDataSource {
    suspend fun insertHistorySql(history: UiHistoryItem): Resource<Boolean>
    suspend fun insertHistorySupabase(history: UiHistoryItem): Resource<Boolean>
    suspend fun getHistorySql(): Resource<List<HistoryEntity>>

}