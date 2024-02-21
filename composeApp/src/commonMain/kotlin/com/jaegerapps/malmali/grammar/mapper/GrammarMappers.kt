package com.jaegerapps.malmali.grammar.mapper

import com.jaegerapps.malmali.grammar.models.GrammarPointDTO
import com.jaegerapps.malmali.grammar.models.GrammarLevel
import com.jaegerapps.malmali.grammar.models.GrammarPoint

fun GrammarPointDTO.toGrammarPoint(): GrammarPoint {
    return GrammarPoint(
        grammarCategory = grammar_level,
        grammarTitle = grammar_point,
        grammarDef1 = grammar_explanation_1,
        grammarDef2 = grammar_explanation_2,
        exampleEn1 = explain_1_ex_en,
        exampleEn2 = explain_2_ex_en,
        exampleKo1 = explain_1_ex_ko,
        exampleKo2 = explain_2_ex_ko
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
            title = "Level $number",
            isUnlocked = number == 1,
            grammarList = list
        )
    }
}

