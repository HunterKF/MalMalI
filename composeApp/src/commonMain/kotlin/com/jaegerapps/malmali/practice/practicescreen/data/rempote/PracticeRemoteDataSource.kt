package com.jaegerapps.malmali.practice.practicescreen.data.rempote

import com.jaegerapps.malmali.practice.practicescreen.data.models.HistoryDTO
import com.jaegerapps.malmali.vocabulary.data.models.VocabSetDTO
import core.util.Resource

interface PracticeRemoteDataSource {
    suspend fun insertHistory(history: HistoryDTO)
    suspend fun getDefaultSet(): Resource<VocabSetDTO>
}