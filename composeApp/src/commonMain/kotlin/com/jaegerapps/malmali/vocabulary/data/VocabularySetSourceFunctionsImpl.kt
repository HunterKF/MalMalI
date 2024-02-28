package com.jaegerapps.malmali.vocabulary.data

import VocabularySetSourceFunctions
import com.jaegerapps.malmali.vocabulary.mapper.toFlashSetDto
import com.jaegerapps.malmali.vocabulary.models.VocabSet
import core.data.supabase.SupabaseKeys
import core.util.Resource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class VocabularySetSourceFunctionsImpl(private val client: SupabaseClient) : VocabularySetSourceFunctions {
    override suspend fun addSet(
        vocabSet: VocabSet,
        username: String
    ) {

        client.from(SupabaseKeys.SETS).insert(vocabSet.toFlashSetDto(arrayOf(username))) {
            select()
        }
    }

    override suspend fun getSet(setId: Int, setTitle: String): Resource<VocabSet> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSets(): Resource<List<VocabSet>> {
        TODO("Not yet implemented")
    }


    override suspend fun deleteSet(setId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun updateSet(set: VocabSet) {
        TODO("Not yet implemented")
    }


}
