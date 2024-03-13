package com.jaegerapps.malmali.practice.practicescreen.domain.models

//This is what will display for the practiced history
data class HistoryModel(
    val id: Int,
    val sentence: String,
    val grammar: HistoryGrammarModel,
    val set: HistoryVocabularyModel,
    val dateCreated: Long,
    val isFavorited: Boolean = false
)
