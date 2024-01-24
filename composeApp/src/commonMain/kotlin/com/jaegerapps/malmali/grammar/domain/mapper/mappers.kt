package com.jaegerapps.malmali.grammar.domain.mapper

import com.jaegerapps.malmali.grammar.data.GrammarPointDTO
import com.jaegerapps.malmali.grammar.domain.GrammarLevel
import com.jaegerapps.malmali.grammar.domain.GrammarPoint

fun GrammarPointDTO.toGrammarPoint(): GrammarPoint {
    return GrammarPoint(
        grammarCategory = grammar_level,
        grammarTitle = grammar_point,
        grammarDef1 =grammar_explanation_1 ,
        grammarDef2 = grammar_explanation_2,
        exampleEn1 = explain_1_ex_en,
        exampleEn2 = explain_2_ex_en,
        exampleKo1 = explain_1_ex_ko,
        exampleKo2 = explain_2_ex_ko
    )
}

fun List<GrammarPoint>.toGrammarLevels(): List<GrammarLevel> {
    return (1..6).map { number ->
        GrammarLevel(
            title = "Level $number",
            isUnlocked = number == 1,
            grammarList = this.filter {
                it.grammarCategory == number
            }
        )
    }
}