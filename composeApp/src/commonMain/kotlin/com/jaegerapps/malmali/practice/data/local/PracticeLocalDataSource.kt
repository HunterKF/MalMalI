package com.jaegerapps.malmali.practice.data.local

import com.jaegerapps.malmali.practice.models.HistoryDTO
import com.jaegerapps.malmali.practice.models.HistoryEntity
import kotlinx.coroutines.flow.Flow

interface PracticeLocalDataSource {
    suspend fun insertHistory(dto: HistoryDTO): Boolean
    fun getHistory(): Flow<List<HistoryEntity>>
}