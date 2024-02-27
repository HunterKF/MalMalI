package com.jaegerapps.malmali.grammar.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GrammarPointDTO(
    //DTOs are sent to the database
    @SerialName("id") val id: Int,
    @SerialName("grammar_point") val grammar_point: String,
    @SerialName("grammar_explanation_1") val grammar_explanation_1: String,
    @SerialName("explain_1_ex_ko") val explain_1_ex_ko: String,
    @SerialName("explain_1_ex_en") val explain_1_ex_en: String,
    @SerialName("grammar_explanation_2") val grammar_explanation_2: String?,
    @SerialName("explain_2_ex_ko") val explain_2_ex_ko: String,
    @SerialName("explain_2_ex_en") val explain_2_ex_en: String,
    @SerialName("grammar_level") val grammar_level: Int,
)

