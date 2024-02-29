package com.jaegerapps.malmali.practice.data.rempote

import com.jaegerapps.malmali.practice.models.HistoryDTO
import core.Knower
import core.Knower.e
import core.util.Resource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.from

class PracticeRemoteDataSourceImpl(
    private val client: SupabaseClient,
) : PracticeRemoteDataSource {
    override suspend fun insertHistory(history: HistoryDTO) {
        try {
            client.from("practice").insert(history) {
                select()
            }

        } catch (e: RestException) {
            e.printStackTrace()
            Knower.e("InsertHistorySupabase", "An error has occurred here. ${e.error}")

        }
    }
}