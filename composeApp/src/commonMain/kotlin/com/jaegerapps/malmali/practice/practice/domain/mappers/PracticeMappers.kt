package com.jaegerapps.malmali.practice.practice.domain.mappers

import com.jaegerapps.malmali.common.mappers.booleanToLong
import com.jaegerapps.malmali.common.mappers.longToBoolean
import com.jaegerapps.malmali.common.models.GrammarPointModel
import com.jaegerapps.malmali.common.models.VocabularyCardModel
import com.jaegerapps.malmali.composeApp.database.History
import com.jaegerapps.malmali.practice.practice.data.models.HistoryDTO
import com.jaegerapps.malmali.practice.practice.data.models.HistoryEntity
import com.jaegerapps.malmali.practice.practice.domain.models.HistoryGrammarModel
import com.jaegerapps.malmali.practice.practice.domain.models.HistoryModel
import com.jaegerapps.malmali.practice.practice.domain.models.HistoryVocabularyModel
import kotlinx.datetime.Clock

fun HistoryModel.toHistoryDTO(): HistoryDTO {
    return HistoryDTO(
        input_sentence = this.sentence,
        grammar_point = this.grammar.grammarTitle,
        grammar_definition_1 = this.grammar.grammarDef1,
        grammar_definition_2 = this.grammar.grammarDef2,
        grammar_level = this.grammar.grammarCategory.toString(),
        vocabulary_word = this.set.word,
        vocabulary_definition = this.set.definition,
        date_created = Clock.System.now().toEpochMilliseconds(),
        is_favorited = booleanToLong(this.isFavorited)
    )
}

fun HistoryModel.toHistoryEntity(): HistoryEntity {
    return HistoryEntity(
        id = this.id,
        input_sentence = this.sentence,
        grammar_point = this.grammar.grammarTitle,
        grammar_definition_1 = this.grammar.grammarDef1,
        grammar_definition_2 = this.grammar.grammarDef2,
        grammar_level = this.grammar.grammarCategory.toString(),
        vocabulary_word = this.set.word,
        vocabulary_definition = this.set.definition,
        date_created = Clock.System.now().toEpochMilliseconds(),
        is_favorited = booleanToLong(this.isFavorited)
    )
}

fun History.toHistoryEntity(): HistoryEntity {
    return HistoryEntity(
        id = this.set_id.toInt(),
        input_sentence = this.input_sentence,
        grammar_point = this.grammar_point,
        grammar_definition_1 = this.grammar_definition_1,
        grammar_definition_2 = this.grammar_definition_2,
        grammar_level = this.grammar_category,
        vocabulary_word = this.vocabulary_word,
        vocabulary_definition = this.vocabulary_definition,
        date_created = this.date_created,
        is_favorited = this.is_favorited
    )
}


fun HistoryEntity.toHistoryModel(): HistoryModel {
    return HistoryModel(
        id = this.id,
        sentence = this.input_sentence,
        grammar = this.toHistoryGrammarModel(),
        set = this.toHistoryVocabularyModel(),
        isFavorited = longToBoolean(this.is_favorited),
        dateCreated = this.date_created
    )
}

fun HistoryEntity.toHistoryGrammarModel(): HistoryGrammarModel {
    return HistoryGrammarModel(
        grammarCategory = this.grammar_level.toInt(),
        grammarTitle = this.grammar_point,
        grammarDef1 = this.grammar_definition_1,
        grammarDef2 = this.grammar_definition_2
    )
}

fun HistoryEntity.toHistoryVocabularyModel(): HistoryVocabularyModel {
    return HistoryVocabularyModel(
        word = this.vocabulary_word,
        definition = this.vocabulary_definition
    )
}

fun createHistoryModel(
    sentence: String,
    grammar: GrammarPointModel,
    set: VocabularyCardModel,
): HistoryModel {
    return HistoryModel(
        id = 0,
        sentence = sentence,
        grammar = grammar.toHistoryGrammarModel(),
        set = set.toHistoryVocabularyModel(),
        dateCreated = Clock.System.now().toEpochMilliseconds()
    )
}

fun GrammarPointModel.toHistoryGrammarModel(): HistoryGrammarModel {
    return HistoryGrammarModel(
        grammarCategory, grammarTitle, grammarDef1, grammarDef2
    )
}

fun VocabularyCardModel.toHistoryVocabularyModel(): HistoryVocabularyModel {
    return HistoryVocabularyModel(word, definition)
}
