package com.jaegerapps.malmali.practice.practice.data.models

data class HistoryDTO(
    val input_sentence: String,
    val grammar_point: String,
    val grammar_definition_1: String,
    val grammar_definition_2: String?,
    val grammar_level: String,
    val vocabulary_word: String,
    val vocabulary_definition: String,
    val date_created: Long,
    val is_favorited: Long,
)
