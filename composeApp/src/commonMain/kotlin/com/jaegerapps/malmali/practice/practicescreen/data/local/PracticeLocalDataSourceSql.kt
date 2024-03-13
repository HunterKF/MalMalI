package com.jaegerapps.malmali.practice.practicescreen.data.local

import com.jaegerapps.malmali.practice.practicescreen.data.models.HistoryDTO
import com.jaegerapps.malmali.practice.practicescreen.data.models.HistoryEntity
import com.jaegerapps.malmali.vocabulary.data.models.SetEntity
import core.util.Resource
import kotlinx.coroutines.flow.Flow

interface PracticeLocalDataSourceSql {
    suspend fun insertHistory(entity: HistoryEntity): Boolean
    fun getHistory(): Flow<List<HistoryEntity>>
    suspend fun getSets(): Resource<List<SetEntity>>
}