package com.jaegerapps.malmali.practice.practice_settings.data.local

import com.russhwolf.settings.Settings
import core.Knower
import core.Knower.e
import core.data.settings.SettingKeys
import core.util.Resource
import core.util.toDbString
import core.util.toListString
import io.ktor.utils.io.errors.IOException

class PracticeSettingLocalDataSourceSettingsImpl(
    private val settings: Settings,
) : PracticeSettingLocalDataSourceSettings {
    override suspend fun readSelectedLevels(): Resource<List<Int>> {
        return try {
            val result =
                settings.getString(SettingKeys.LEVELS, "-1").toListString().map { it.toInt() }
            Resource.Success(result)
        } catch (e: IOException) {
            e.printStackTrace()
            Knower.e("readSelectedLevels", "An error occurred in PracticeSettingsSettingsImpl: ${e.message}")
            Resource.Error(e)
        }
    }

    override suspend fun updateSelectedLevels(levels: List<Int>): Resource<List<Int>> {
        return try {
            settings.putString(SettingKeys.LEVELS, levels.toDbString())
            Resource.Success(levels)
        } catch (e: IOException) {
            e.printStackTrace()
            Knower.e("updateSelectedLevels", "An error occurred in PracticeSettingsSettingsImpl: ${e.message}")
            Resource.Error(e)
        }
    }

    override suspend fun readSelectedSetIds(): Resource<List<Int>> {
        return try {
            val result = settings.getString(SettingKeys.SETS, "-1").toListString().map { it.toInt() }
            Resource.Success(result)
        } catch (e: IOException) {
            e.printStackTrace()
            Knower.e("updateSelectedLevels", "An error occurred in PracticeSettingsSettingsImpl: ${e.message}")
            Resource.Error(e)
        }
    }

    override suspend fun updateSelectedSets(list: List<Int>): Resource<List<Int>> {
        return try {
            settings.putString(SettingKeys.LEVELS, list.toDbString())
            Resource.Success(list)
        } catch (e: IOException) {
            e.printStackTrace()
            Knower.e("updateSelectedSets", "An error occurred in PracticeSettingsSettingsImpl: ${e.message}")
            Resource.Error(e)
        }
    }

    override suspend fun toggleTranslationSetting(value: Boolean): Resource<Boolean> {
        return try {
            settings.putBoolean(SettingKeys.TRANSLATION_SETTING, value)
            Resource.Success(value)
        } catch (e: IOException) {
            e.printStackTrace()
            Knower.e("updateSelectedSets", "An error occurred in PracticeSettingsSettingsImpl: ${e.message}")
            Resource.Error(e)
        }
    }

    override suspend fun toggleTeacherSetting(value: Boolean): Resource<Boolean> {
        return try {
            settings.putBoolean(SettingKeys.TEACHER_SETTING, value)
            Resource.Success(value)
        } catch (e: IOException) {
            e.printStackTrace()
            Knower.e("updateSelectedSets", "An error occurred in PracticeSettingsSettingsImpl: ${e.message}")
            Resource.Error(e)
        }
    }
}