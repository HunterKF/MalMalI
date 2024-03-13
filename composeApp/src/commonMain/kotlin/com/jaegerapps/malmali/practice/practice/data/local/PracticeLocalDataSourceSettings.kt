package com.jaegerapps.malmali.practice.practice.data.local

import core.util.Resource

interface PracticeLocalDataSourceSettings {
    suspend fun readSelectedLevels(): Resource<List<Int>>
    suspend fun readSelectedSets(): Resource<List<Int>>

    suspend fun updateSelectedLevels(list: List<Int>): Resource<List<Int>>
    suspend fun updateSelectedSets(list: List<Int>): Resource<List<Int>>
}