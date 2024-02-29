package com.jaegerapps.malmali.vocabulary.data.repo

import com.jaegerapps.malmali.vocabulary.domain.repo.VocabularyRepo
import com.jaegerapps.malmali.vocabulary.domain.mapper.toVocabSetDTO
import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel
import core.data.supabase.SupabaseKeys
import core.util.Resource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class VocabularyRepoImpl(private val client: SupabaseClient) : VocabularyRepo {
    override suspend fun addSet(
        vocabSetModel: VocabSetModel,
        username: String
    ) {

        client.from(SupabaseKeys.SETS).insert(vocabSetModel.toVocabSetDTO(arrayOf(username))) {
            select()
        }
    }

    override suspend fun getSet(setId: Int, setTitle: String): Resource<VocabSetModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSets(): Resource<List<VocabSetModel>> {
        TODO("Not yet implemented")
    }


    override suspend fun deleteSet(setId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun updateSet(set: VocabSetModel) {
        TODO("Not yet implemented")
    }


}
