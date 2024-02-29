package com.jaegerapps.malmali.vocabulary.data.remote

import com.jaegerapps.malmali.vocabulary.data.models.VocabSetDTO
import core.Knower
import core.Knower.d
import core.data.supabase.SupabaseKeys
import core.util.Resource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from

class VocabularyRemoteDataSourceImpl(
    private val client: SupabaseClient,
) : VocabularyRemoteDataSource {

    override suspend fun createSet(vocabSet: VocabSetDTO): Resource<VocabSetDTO> {
        return try {
            val result = client.from(SupabaseKeys.SETS).insert(vocabSet).decodeSingle<VocabSetDTO>()
            Knower.d("createSet", "Here is the result: $result")
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

    override suspend fun readAllSets(): Resource<List<VocabSetDTO>> {
        return try {
            val result = client.from(SupabaseKeys.SETS).select {

            }.decodeList<VocabSetDTO>()
            Resource.Success(result)
        } catch (e: RestException) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun updateSet(vocabSet: VocabSetDTO): Resource<VocabSetDTO> {
        return try {
            val result = client.from(SupabaseKeys.SETS).update(vocabSet) {
                filter {
                    eq("id", vocabSet.id)
                }
            }.decodeAs<VocabSetDTO>()
            Resource.Success(result)
        } catch (e: RestException) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun deleteSet(vocabSet: VocabSetDTO): Resource<Boolean> {
        return try {
            client.from(SupabaseKeys.SETS).delete {
                filter {
                    eq("id", vocabSet.id)
                }
            }
            Resource.Success(true)
        } catch (e: RestException) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}