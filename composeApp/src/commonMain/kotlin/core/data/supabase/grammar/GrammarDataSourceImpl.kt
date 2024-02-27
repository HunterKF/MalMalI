package core.data.supabase.grammar

import com.jaegerapps.malmali.composeApp.database.MalMalIDatabase
import com.jaegerapps.malmali.grammar.models.GrammarPoint
import core.Knower
import core.Knower.e
import core.domain.supabase.grammar.GrammarDataSource
import core.util.Resource

class GrammarDataSourceImpl(
    private val database: MalMalIDatabase,
) : GrammarDataSource {
    //This is used on start up to see if grammar exists and then to save grammar if it doesn't
    override suspend fun grammarExists(): Resource<Boolean> {
        return try {
            Resource.Success(database.flashCardsQueries.countGrammar().executeAsOne() == 0L)
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("grammarExists", "Grammar has failed. ${e.message}")
            Resource.Error(e)
        }
    }

    override suspend fun insertGrammar(grammar: List<GrammarPoint>): Resource<Boolean> {
        return try {
            grammar.forEach {
                database.flashCardsQueries.insertGrammar(
                    set_id = null,
                    grammar_category = it.grammarCategory.toLong(),
                    grammar_title = it.grammarTitle,
                    grammar_definition_1 = it.grammarDef1,
                    grammar_definition_2 = it.grammarDef2,
                    example_eng_1 = it.exampleEng1,
                    example_eng_2 = it.exampleEng2,
                    example_kor_1 = it.exampleKor1,
                    example_kor_2 = it.exampleKor2
                )
            }
            Resource.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("updateGrammar", "An error has occurred here. ${e.message}")
            Resource.Error(Throwable())
        }
    }

    override suspend fun updateGrammar(grammar: List<GrammarPoint>): Resource<Boolean> {
        return try {
            grammar.forEach {
                database.flashCardsQueries.insertGrammar(
                    set_id = null,
                    grammar_category = it.grammarCategory.toLong(),
                    grammar_title = it.grammarTitle,
                    grammar_definition_1 = it.grammarDef1,
                    grammar_definition_2 = it.grammarDef2,
                    example_eng_1 = it.exampleEng1,
                    example_eng_2 = it.exampleEng2,
                    example_kor_1 = it.exampleKor1,
                    example_kor_2 = it.exampleKor2
                )
            }
            Resource.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Knower.e("updateGrammar", "An error has occurred here. ${e.message}")
            Resource.Error(Throwable())
        }
    }
}