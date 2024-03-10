package com.jaegerapps.malmali.grammar.data.repo

import com.jaegerapps.malmali.grammar.data.local.GrammarLocalDataSourceSettings
import com.jaegerapps.malmali.grammar.domain.repo.GrammarRepo
import core.Knower
import core.Knower.e
import core.util.Resource
import core.util.toDbString
import core.util.toListInt
import core.util.toListString

class GrammarRepoImpl(
    private val local: GrammarLocalDataSourceSettings,
) : GrammarRepo {
    override suspend fun readSelectedLevels(): Resource<List<Int>> {
        return try {
            val result = local.readSelectedLevels()
            if (!result.data.isNullOrEmpty()) {
                Resource.Success(result.data.toListInt())
            } else {
                Resource.Error(Throwable())
            }
        } catch (e: Exception) {
            Knower.e("readSelectedLevels", "An error has occurred: ${e.message}")
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun updateSelectedLevels(levels: List<Int>): Resource<List<Int>> {
        return try {
            val result = local.updateSelectedLevels(levels.toDbString())
            if (!result.data.isNullOrEmpty()) {
                Resource.Success(result.data.toListInt())
            } else {
                Resource.Error(Throwable())
            }
        } catch (e: Exception) {
            Knower.e("readSelectedLevels", "An error has occurred: ${e.message}")
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}