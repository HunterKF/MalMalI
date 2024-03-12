package com.jaegerapps.malmali.practice.data.rempote

import com.jaegerapps.malmali.practice.data.models.HistoryDTO
import com.jaegerapps.malmali.vocabulary.data.models.VocabSetDTO
import core.Knower
import core.Knower.e
import core.data.supabase.SupabaseKeys
import core.util.Resource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.from

class PracticeRemoteDataSourceImpl(
    private val client: SupabaseClient,
) : PracticeRemoteDataSource {
    override suspend fun insertHistory(history: HistoryDTO) {
        try {
            client.from(SupabaseKeys.PRACTICE).insert(history) {
                select()
            }

        } catch (e: RestException) {
            e.printStackTrace()
            Knower.e("InsertHistorySupabase", "An error has occurred here. ${e.error}")

        }
    }

    override suspend fun getDefaultSet(): Resource<VocabSetDTO> {
        return try {
            val result = client.from(SupabaseKeys.SETS).select {
                filter {
                    eq("set_title", "Korean 101")
                }
            }

            val decoded = result.decodeSingle<VocabSetDTO>()
            Resource.Success(decoded)
        } catch (e: RestException) {
            e.printStackTrace()
            Knower.e("getDefaultSet", "An error occurred in PracticeRemoteDataSourceImpl: ${e.message}")
            Resource.Error(e)
        }
    }
}