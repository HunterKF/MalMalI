package com.jaegerapps.malmali.grammar.data.local

import com.russhwolf.settings.Settings
import core.data.settings.SettingKeys
import core.util.Resource

class GrammarLocalDataSourceSettingsImpl(
    private val settings: Settings
): GrammarLocalDataSourceSettings {
    override suspend fun readSelectedLevels(): Resource<String> {
        return try {
            val result = settings.getString(SettingKeys.LEVELS, "1")
            if (result.isBlank()) {
                settings.putString(SettingKeys.LEVELS, "1")
                val newResult = settings.getString(SettingKeys.LEVELS, "1")
                Resource.Success(newResult)

            } else {
                Resource.Success(result)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun updateSelectedLevels(list: String): Resource<String> {
        return try {
            settings.putString(SettingKeys.LEVELS, list)
            Resource.Success(list)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}