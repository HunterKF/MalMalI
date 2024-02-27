package core.domain

import com.jaegerapps.malmali.grammar.models.GrammarLevel
import com.jaegerapps.malmali.grammar.models.GrammarPoint
import core.util.Resource

interface GrammarDataSource {
    suspend fun grammarExists(): Resource<Boolean>
    suspend fun insertGrammar(grammar: List<GrammarPoint>): Resource<Boolean>
    suspend fun updateGrammar(grammar: List<GrammarPoint>): Resource<Boolean>
}