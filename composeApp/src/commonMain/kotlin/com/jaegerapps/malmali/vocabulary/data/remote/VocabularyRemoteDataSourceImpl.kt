package com.jaegerapps.malmali.vocabulary.data.remote

import com.jaegerapps.malmali.vocabulary.data.models.VocabSetDTO
import com.jaegerapps.malmali.vocabulary.data.models.VocabSetDTOWithoutData
import core.Knower
import core.Knower.d
import core.Knower.e
import core.data.supabase.SupabaseKeys
import core.util.Resource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order

class VocabularyRemoteDataSourceImpl(
    private val client: SupabaseClient,
) : VocabularyRemoteDataSource {

    override suspend fun createSet(vocabSet: VocabSetDTOWithoutData): Resource<VocabSetDTO> {
        return try {
            val result = client.from(SupabaseKeys.SETS).insert(vocabSet) {
                select()
            }.decodeSingle<VocabSetDTO>()
            Resource.Success(result)
        } catch (e: RestException) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun readSingleSet(setId: Int): Resource<VocabSetDTO> {
        return try {
            val result = client.from(SupabaseKeys.SETS).select {
                filter {
                    eq("id", setId)
                }
            }.decodeSingle<VocabSetDTO>()
            Resource.Success(result)
        } catch (e: RestException) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun readAllSets(start: Long, end: Long): Resource<List<VocabSetDTO>> {
        return try {
            val result = client.from(SupabaseKeys.SETS).select {
                filter {
                    eq("is_public", true)
                }
                order("set_title", order = Order.ASCENDING)
                range(start, end)
            }.decodeList<VocabSetDTO>()
            Resource.Success(result)
        } catch (e: RestException) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun readBySearch(
        title: String,
        start: Long,
        end: Long,
    ): Resource<List<VocabSetDTO>> {
        return try {
            val result = client.from(SupabaseKeys.SETS).select {
                filter {
                    imatch("set_title", title)
                }
                order("set_title", order = Order.ASCENDING)
                range(start, end)
            }.decodeList<VocabSetDTO>()
            Resource.Success(result)
        } catch (e: RestException) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun updateSet(vocabSet: VocabSetDTO): Resource<VocabSetDTO> {
        return try {

            val result = client.from(SupabaseKeys.SETS).update(
                {
                    set("vocabulary_word", vocabSet.vocabulary_word)
                    set("vocabulary_definition", vocabSet.vocabulary_definition)
                    set("tags", vocabSet.tags)
                    set("set_title", vocabSet.set_title)
                    set("set_icon", vocabSet.set_icon)
                    set("is_public", vocabSet.is_public)
                }
            ) {
                select()
                filter {
                    eq("id", vocabSet.id!!)
                }
            }
            val decoder = result.decodeSingle<VocabSetDTO>()

            Resource.Success(decoder)
        } catch (e: RestException) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun deleteSet(remoteId: Int): Resource<Boolean> {
        return try {
            client.from(SupabaseKeys.SETS).delete {
                filter {
                    eq("id", remoteId)
                }
            }

            Resource.Success(true)
        } catch (e: RestException) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}