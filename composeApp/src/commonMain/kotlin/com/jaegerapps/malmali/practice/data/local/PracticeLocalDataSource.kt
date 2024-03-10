package com.jaegerapps.malmali.practice.data.local

import com.jaegerapps.malmali.practice.data.models.HistoryDTO
import com.jaegerapps.malmali.practice.data.models.HistoryEntity
import kotlinx.coroutines.flow.Flow

interface PracticeLocalDataSource {
    suspend fun insertHistory(dto: HistoryDTO): Boolean
    fun getHistory(): Flow<List<HistoryEntity>>
}