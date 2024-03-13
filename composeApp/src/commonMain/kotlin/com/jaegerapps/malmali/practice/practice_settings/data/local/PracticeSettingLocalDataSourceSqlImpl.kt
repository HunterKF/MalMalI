package com.jaegerapps.malmali.practice.practice_settings.data.local

import com.jaegerapps.malmali.composeApp.database.MalMalIDatabase
import com.jaegerapps.malmali.vocabulary.data.models.SetEntity
import com.jaegerapps.malmali.vocabulary.domain.mapper.toSetEntity
import core.Knower
import core.Knower.e
import core.util.Resource

class PracticeSettingLocalDataSourceSqlImpl(
    database: MalMalIDatabase,
) : PracticeSettingLocalDataSourceSql {
    private val queries = database.flashCardsQueries
    override suspend fun readVocabSets(): Resource<List<SetEntity>> {
        return try {
            val result = queries.selectAllSets()
                .executeAsList()
                .map { it.toSetEntity() }
            Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e(
                "PracticeSettingsLocalDataSourceSqlImpl - readVpcabSets",
                "An error occurred here: ${e.message}"
            )
            Resource.Error(e)
        }
    }
}