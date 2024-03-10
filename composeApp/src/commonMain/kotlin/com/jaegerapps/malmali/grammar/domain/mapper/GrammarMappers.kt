package com.jaegerapps.malmali.grammar.domain.mapper

import com.jaegerapps.malmali.grammar.models.GrammarPointDTO
import com.jaegerapps.malmali.grammar.models.GrammarLevel
import com.jaegerapps.malmali.grammar.models.GrammarPoint

fun GrammarPointDTO.toGrammarPoint(): GrammarPoint {
    return GrammarPoint(
        grammarCategory = grammar_level,
        grammarTitle = grammar_point,
        grammarDef1 = grammar_explanation_1,
        grammarDef2 = grammar_explanation_2,
        exampleEng1 = explain_1_ex_en,
        exampleEng2 = explain_2_ex_en,
        exampleKor1 = explain_1_ex_ko,
        exampleKor2 = explain_2_ex_ko
    )
}

fun List<GrammarPoint>.toGrammarLevels(): List<GrammarLevel> {
    return (1..6).map { number ->
        val list = if (number == 1) {
            this.filter { it.grammarCategory == 1 }.map { it.copy(selected = true) }
        } else {
            this.filter { it.grammarCategory == number }
        }
        GrammarLevel(
            id = number,
            title = "Level $number",
            isUnlocked = number == 1,
            grammarList = list
        )
    }
}


