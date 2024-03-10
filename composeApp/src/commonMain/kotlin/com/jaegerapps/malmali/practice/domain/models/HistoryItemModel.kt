package com.jaegerapps.malmali.practice.domain.models

import com.jaegerapps.malmali.grammar.domain.models.GrammarPointModel
import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel
import com.jaegerapps.malmali.vocabulary.domain.models.VocabularyCardModel

//This is what will display for the practiced history
data class HistoryItemModel(
    val id: Int,
    val sentence: String,
    val grammar: GrammarPointModel,
    val set: VocabularyCardModel,
    val isSaved: Boolean = false
)
