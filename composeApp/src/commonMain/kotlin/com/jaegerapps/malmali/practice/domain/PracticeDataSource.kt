package com.jaegerapps.malmali.practice.domain

import com.jaegerapps.malmali.grammar.models.GrammarLevel
import com.jaegerapps.malmali.practice.models.HistoryEntity
import com.jaegerapps.malmali.practice.models.UiHistoryItem
import com.jaegerapps.malmali.vocabulary.models.VocabSet
import core.util.Resource
import kotlinx.coroutines.flow.Flow

interface PracticeDataSource {
    suspend fun insertHistorySql(history: UiHistoryItem): Resource<Boolean>
    suspend fun insertHistorySupabase(history: UiHistoryItem): Resource<Boolean>
    fun getHistorySql(): Flow<List<HistoryEntity>>

}