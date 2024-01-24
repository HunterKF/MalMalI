package com.jaegerapps.malmali.grammar.data

import com.jaegerapps.malmali.grammar.domain.GrammarLevel
import com.jaegerapps.malmali.grammar.domain.GrammarRepo
import com.jaegerapps.malmali.grammar.domain.mapper.toGrammarLevels
import com.jaegerapps.malmali.grammar.domain.mapper.toGrammarPoint
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class GrammarRepoImpl(
    private val client: SupabaseClient,
) : GrammarRepo {
    override suspend fun getGrammar(): List<GrammarLevel> {
        val data = client.from("grammar").select().decodeList<GrammarPointDTO>()
        val list = data.map { it.toGrammarPoint() }
        return list.toGrammarLevels()
    }

}