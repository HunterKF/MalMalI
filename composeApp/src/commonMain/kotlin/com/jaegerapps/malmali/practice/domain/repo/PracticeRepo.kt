package com.jaegerapps.malmali.practice.domain.repo

import com.jaegerapps.malmali.practice.data.models.HistoryEntity
import com.jaegerapps.malmali.practice.domain.models.HistoryModel
import com.jaegerapps.malmali.common.models.VocabularySetModel
import core.util.Resource
import kotlinx.coroutines.flow.Flow

interface PracticeRepo {
    //settings
    suspend fun readSelectedLevels(): Resource<List<Int>>
    suspend fun updateSelectedLevels(levels: List<Int>): Resource<List<Int>>

    suspend fun readSelectedSetIds(): Resource<List<Int>>
    suspend fun updateSelectedSets(list: List<Int>): Resource<List<Int>>
    //read from sql
    suspend fun readVocabSets(): Resource<List<VocabularySetModel>>

    suspend fun insertHistorySql(history: HistoryModel): Resource<Boolean>
    suspend fun insertHistorySupabase(history: HistoryModel): Resource<Boolean>
    suspend fun getDefaultSet(): Resource<VocabularySetModel>
    fun getHistorySql(): Flow<List<HistoryEntity>>

}