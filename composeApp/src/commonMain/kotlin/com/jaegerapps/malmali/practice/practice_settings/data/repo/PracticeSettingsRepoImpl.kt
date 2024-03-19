package com.jaegerapps.malmali.practice.practice_settings.data.repo

import com.jaegerapps.malmali.common.models.VocabularySetModel
import com.jaegerapps.malmali.practice.practice_settings.data.local.PracticeSettingLocalDataSourceSettings
import com.jaegerapps.malmali.practice.practice_settings.data.local.PracticeSettingLocalDataSourceSql
import com.jaegerapps.malmali.practice.practice_settings.domain.mappers.toPracticeSetModel
import com.jaegerapps.malmali.practice.practice_settings.domain.models.PracticeSetModel
import com.jaegerapps.malmali.practice.practice_settings.domain.repo.PracticeSettingsRepo
import com.jaegerapps.malmali.vocabulary.domain.mapper.toVocabSet
import core.util.Resource

class PracticeSettingsRepoImpl(
    private val localSettings: PracticeSettingLocalDataSourceSettings,
    private val localSql: PracticeSettingLocalDataSourceSql
): PracticeSettingsRepo {
    override suspend fun readSelectedLevels(): Resource<List<Int>> {
        return localSettings.readSelectedLevels()
    }

    override suspend fun updateSelectedLevels(levels: List<Int>): Resource<List<Int>> {
        return localSettings.updateSelectedLevels(levels)

    }

    override suspend fun readSelectedSetIds(): Resource<List<Int>> {
       return localSettings.readSelectedSetIds()
    }

    override suspend fun updateSelectedSets(list: List<Int>): Resource<List<Int>> {
       return localSettings.updateSelectedSets(list)
    }

    override suspend fun toggleTranslationSetting(value: Boolean): Resource<Boolean> {
        return localSettings.toggleTranslationSetting(value)
    }

    override suspend fun toggleTeacherSetting(value: Boolean): Resource<Boolean> {
        return localSettings.toggleTeacherSetting(value)

    }

    override suspend fun readVocabSets(): Resource<List<PracticeSetModel>> {
        val result =  localSql.readVocabSets().data?.map { it.toPracticeSetModel() }
        return if (result != null) {
            Resource.Success(result)
        } else {
            Resource.Error(Throwable())
        }
    }
}