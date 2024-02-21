package com.jaegerapps.malmali.grammar.models

import kotlinx.serialization.Serializable

@Serializable
class GrammarPointDTO(
    val id: Int,
    val grammar_level: Int,
    val grammar_point: String,
    val grammar_explanation_1: String,
    val explain_1_ex_ko: String,
    val explain_1_ex_en: String,
    val grammar_explanation_2: String?,
    val explain_2_ex_ko: String,
    val explain_2_ex_en: String,
)

