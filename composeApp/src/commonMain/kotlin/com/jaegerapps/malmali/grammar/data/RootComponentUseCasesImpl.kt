package com.jaegerapps.malmali.grammar.data

import com.jaegerapps.malmali.grammar.domain.RootComponentUseCases
import com.jaegerapps.malmali.grammar.mapper.toGrammarLevels
import com.jaegerapps.malmali.grammar.mapper.toGrammarPoint
import com.jaegerapps.malmali.grammar.models.GrammarLevel
import com.jaegerapps.malmali.grammar.models.GrammarPointDTO
import com.jaegerapps.malmali.vocabulary.models.FlashSetEntity
import core.Knower
import core.Knower.d
import core.Knower.e
import core.data.supabase.SupabaseKeys
import core.util.Resource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.from

class RootComponentUseCasesImpl(
    private val client: SupabaseClient,
) : RootComponentUseCases {
    override suspend fun getGrammar(): Resource<List<GrammarLevel>> {

        return try {
            val data = client.from("grammar").select()
            Knower.d("getGrammar", "Here is the data: ${data.data}")

            val decode = data.decodeList<GrammarPointDTO>()
            Knower.d("getGrammar", "Here is the decode: ${decode}")

            val list = decode.map { it.toGrammarPoint() }
            Resource.Success(list.toGrammarLevels())
        } catch (e: RestException) {
            e.printStackTrace()
            Knower.e("getGrammar", "An error has occurred here. ${e.error}")
            Resource.Error(Throwable(e))

        }
    }

    override suspend fun getSets(name: String): Resource<List<FlashSetEntity>> {
        return try {

            val data = client.from(SupabaseKeys.SETS).select{
                filter {
                    overlaps("subscribed_users", listOf(name))
                }
            }
            val baseSet = client.from(SupabaseKeys.SETS).select {
                filter {
                    exact("author_id", null)
                }
            }
            Knower.d("getSets", "Here is the data: ${data.data}")

            var decode = data.decodeList<FlashSetEntity>()
            decode = decode.plus(baseSet.decodeSingle<FlashSetEntity>())
            Knower.d("getSets", "Here is the decode: ${decode}")

            Resource.Success(decode)
        } catch (e: RestException) {
            e.printStackTrace()
            Knower.e("getGrammar", "An error has occurred here. ${e.error}")
            Resource.Error(Throwable(e))

        }
    }

}