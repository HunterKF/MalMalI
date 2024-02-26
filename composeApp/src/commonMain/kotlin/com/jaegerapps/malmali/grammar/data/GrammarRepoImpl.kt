package com.jaegerapps.malmali.grammar.data

import com.jaegerapps.malmali.grammar.domain.GrammarRepo
import com.jaegerapps.malmali.grammar.mapper.toGrammarLevels
import com.jaegerapps.malmali.grammar.mapper.toGrammarPoint
import com.jaegerapps.malmali.grammar.models.GrammarLevel
import com.jaegerapps.malmali.grammar.models.GrammarPointDTO
import core.Knower
import core.Knower.e
import core.util.Resource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.from

class GrammarRepoImpl(
    private val client: SupabaseClient,
) : GrammarRepo {
    override suspend fun getGrammar(): Resource<List<GrammarLevel>> {

        return try {
            val data = client.from("grammar").select().decodeList<GrammarPointDTO>()
            val list = data.map { it.toGrammarPoint() }
            Resource.Success(list.toGrammarLevels())
        } catch (e: RestException) {
            e.printStackTrace()
            Knower.e("getGrammar", "An error has occurred here. ${e.error}")
            Resource.Error(Throwable(e))

        }
    }

}