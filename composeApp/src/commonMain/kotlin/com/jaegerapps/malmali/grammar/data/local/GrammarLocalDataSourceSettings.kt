package com.jaegerapps.malmali.grammar.data.local

import core.util.Resource

interface GrammarLocalDataSourceSettings {
    suspend fun readSelectedLevels(): Resource<String>
    suspend fun updateSelectedLevels(list: String): Resource<String>
}