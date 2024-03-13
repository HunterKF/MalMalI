package com.jaegerapps.malmali.practice.practice_settings.data.local

import core.util.Resource

interface PracticeSettingLocalDataSourceSettings {
    suspend fun readSelectedLevels(): Resource<List<Int>>
    suspend fun updateSelectedLevels(levels: List<Int>): Resource<List<Int>>

    suspend fun readSelectedSetIds(): Resource<List<Int>>
    suspend fun updateSelectedSets(list: List<Int>): Resource<List<Int>>

    suspend fun toggleTranslationSetting(value: Boolean): Resource<Boolean>
    suspend fun toggleTeacherSetting(value: Boolean): Resource<Boolean>
}