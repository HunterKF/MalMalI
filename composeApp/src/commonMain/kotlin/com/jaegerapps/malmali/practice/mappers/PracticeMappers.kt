package com.jaegerapps.malmali.practice.mappers

import com.jaegerapps.malmali.composeApp.database.History
import com.jaegerapps.malmali.practice.models.HistoryDTO
import com.jaegerapps.malmali.practice.models.HistoryEntity
import com.jaegerapps.malmali.practice.models.UiHistoryItem
import kotlinx.datetime.Clock

fun UiHistoryItem.toHistoryDTO(): HistoryDTO {
    return HistoryDTO(
        input_sentence= this.sentence,
        grammar_point = this.grammar.grammar,
        grammar_definition_1 = this.grammar.definition1,
        grammar_definition_2 = this.grammar.definition2,
        grammar_level = this.grammar.level,
        vocabulary_word = this.vocab.word ,
        vocabulary_definition =this.vocab.definition ,
        date_created = Clock.System.now().toEpochMilliseconds(),
        is_favorited = if (!this.isSaved) 0 else 1
    )
}

fun History.toHistoryEntity(): HistoryEntity {
    return HistoryEntity(
        input_sentence= this.input_sentence,
        grammar_point = this.grammar_point,
        grammar_definition_1 = this.grammar_definition_1,
        grammar_definition_2 = this.grammar_definition_2,
        grammar_level = this.grammar_level,
        vocabulary_word = this.vocabulary_word,
        vocabulary_definition =this.vocabulary_definition,
        date_created = this.date_created,
        is_favorited = this.is_favorited.toInt() == 1
    )
}