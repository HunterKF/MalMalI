package com.jaegerapps.malmali.practice.practice.data.local

import com.russhwolf.settings.Settings
import core.Knower
import core.Knower.d
import core.Knower.e
import core.data.settings.SettingKeys
import core.util.Resource
import core.util.toDbString
import core.util.toListString

class PracticeLocalDataSourceSettingsImpl(
    private val settings: Settings
): PracticeLocalDataSourceSettings {
    override suspend fun readSelectedLevels(): Resource<List<Int>> {
        return try {
            Knower.d("readSelectedLevels", settings.getString(SettingKeys.LEVELS, "-1"))

            Resource.Success(settings.getString(SettingKeys.LEVELS, "-1").toListString().map { it.toInt() })
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("readSelectedLevels", "An error occurred. ${e.message}")
            Resource.Error(e)
        }
    }

    override suspend fun readSelectedSets(): Resource<List<Int>> {
        return try {
            Resource.Success(settings.getString(SettingKeys.SETS, "-1").toListString().map { it.toInt() })
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("readSelectedSets", "An error occurred. ${e.message}")
            Resource.Error(e)
        }
    }

    override suspend fun updateSelectedLevels(list: List<Int>): Resource<List<Int>> {
        return try {
            settings.putString(SettingKeys.LEVELS, list.toDbString())
            Resource.Success(list)
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("updateSelectedLevels", "An error occurred. ${e.message}")
            Resource.Error(e)
        }
    }

    override suspend fun updateSelectedSets(list: List<Int>): Resource<List<Int>> {
        return try {
            settings.putString(SettingKeys.SETS, list.toDbString())
            Resource.Success(list)
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("updateSelectedSets", "An error occurred. ${e.message}")
            Resource.Error(e)
        }
    }
}