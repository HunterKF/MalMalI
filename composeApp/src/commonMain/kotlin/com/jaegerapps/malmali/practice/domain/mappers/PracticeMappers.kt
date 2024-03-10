package com.jaegerapps.malmali.practice.domain.mappers

import com.jaegerapps.malmali.composeApp.database.History
import com.jaegerapps.malmali.grammar.domain.models.GrammarLevelModel
import com.jaegerapps.malmali.grammar.domain.models.GrammarPointModel
import com.jaegerapps.malmali.practice.data.models.HistoryDTO
import com.jaegerapps.malmali.practice.data.models.HistoryEntity
import com.jaegerapps.malmali.practice.domain.models.HistoryItemModel
import com.jaegerapps.malmali.practice.domain.models.PracticeGrammarModel
import com.jaegerapps.malmali.practice.domain.models.PracticeGrammarLevelModel
import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel
import com.jaegerapps.malmali.vocabulary.domain.models.VocabularyCardModel
import kotlinx.datetime.Clock

fun HistoryItemModel.toHistoryDTO(): HistoryDTO {
    return HistoryDTO(
        input_sentence = this.sentence,
        grammar_point = this.grammar.grammar,
        grammar_definition_1 = this.grammar.definition1,
        grammar_definition_2 = this.grammar.definition2,
        grammar_level = this.grammar.level,
        vocabulary_word = this.set.word,
        vocabulary_definition = this.set.definition,
        date_created = Clock.System.now().toEpochMilliseconds(),
        is_favorited = if (!this.isSaved) 0 else 1
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
        is_favorited = this.is_favorited.toInt() == 1
    )
}


fun HistoryEntity.toUiHistoryItem(): HistoryItemModel {
    return HistoryItemModel(
        id = this.id,
        sentence = this.input_sentence,
        grammar = this.toUiPracticeGrammar(),
        set = this.toUiPractiveVocab(),
        isSaved = is_favorited
    )
}

private fun HistoryEntity.toUiPractiveVocab(): PracticeVocabularyModel {
    return PracticeVocabularyModel(
        word = this.vocabulary_word,
        definition = this.vocabulary_definition
    )
}

private fun HistoryEntity.toUiPracticeGrammar(): PracticeGrammarModel {
    return PracticeGrammarModel(
        grammar = grammar_point,
        definition1 = grammar_definition_1,
        definition2 = grammar_definition_2,
        level = grammar_level
    )
}

fun List<GrammarLevelModel>.toUiPracticeGrammarList(): List<PracticeGrammarLevelModel> {
    var list = emptyList<PracticeGrammarLevelModel>()
    for (i in 0 until this.size - 1) {

        list = list.plus(PracticeGrammarLevelModel(
            this[i].title,
            false,
            this[i].grammarList.map { it.toUiPracticeGrammar() }
        ))
    }
    return list

}

fun GrammarPointModel.toUiPracticeGrammar(): PracticeGrammarModel {
    return PracticeGrammarModel(
        grammar = this.grammarTitle,
        definition1 = this.grammarDef1,
        definition2 = this.grammarDef2,
        level = this.grammarCategory.toString()
    )
}

fun VocabSetModel.toUiPracticeVocabList(): List<PracticeVocabularyModel> {
    return this.cards.map { it.toUiPracticeVocab() }
}

fun VocabularyCardModel.toUiPracticeVocab(): PracticeVocabularyModel {
    return PracticeVocabularyModel(
        word = this.word,
        definition = this.definition
    )
}