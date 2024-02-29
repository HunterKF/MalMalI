package com.jaegerapps.malmali.practice.data.rempote

import com.jaegerapps.malmali.practice.models.HistoryDTO

interface PracticeRemoteDataSource {
    suspend fun insertHistory(history: HistoryDTO)
}