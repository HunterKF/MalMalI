package com.jaegerapps.malmali.practice.practice_settings.data.local

import com.jaegerapps.malmali.vocabulary.data.models.SetEntity
import core.util.Resource

interface PracticeSettingLocalDataSourceSql {
    suspend fun readVocabSets(): Resource<List<SetEntity>>

}