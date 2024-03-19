package com.jaegerapps.malmali.practice.practice_settings.domain.repo

import com.jaegerapps.malmali.common.models.VocabularySetModel
import com.jaegerapps.malmali.practice.practice_settings.domain.models.PracticeSetModel
import core.util.Resource

interface PracticeSettingsRepo {
    //settings
    suspend fun readSelectedLevels(): Resource<List<Int>>
    suspend fun updateSelectedLevels(levels: List<Int>): Resource<List<Int>>

    suspend fun readSelectedSetIds(): Resource<List<Int>>
    suspend fun updateSelectedSets(list: List<Int>): Resource<List<Int>>

    suspend fun toggleTranslationSetting(value: Boolean): Resource<Boolean>
    suspend fun toggleTeacherSetting(value: Boolean): Resource<Boolean>
    //read from sql
    suspend fun readVocabSets(): Resource<List<PracticeSetModel>>
}