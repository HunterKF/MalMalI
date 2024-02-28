package com.jaegerapps.malmali.practice.mappers

import com.jaegerapps.malmali.composeApp.database.History
import com.jaegerapps.malmali.grammar.mapper.toGrammarLevels
import com.jaegerapps.malmali.grammar.models.GrammarLevel
import com.jaegerapps.malmali.grammar.models.GrammarPoint
import com.jaegerapps.malmali.practice.models.HistoryDTO
import com.jaegerapps.malmali.practice.models.HistoryEntity
import com.jaegerapps.malmali.practice.models.UiHistoryItem
import com.jaegerapps.malmali.practice.models.UiPracticeGrammar
import com.jaegerapps.malmali.practice.models.UiPracticeGrammarLevel
import com.jaegerapps.malmali.practice.models.UiPracticeVocab
import com.jaegerapps.malmali.vocabulary.models.VocabSet
import com.jaegerapps.malmali.vocabulary.models.VocabularyCard
import kotlinx.datetime.Clock

fun UiHistoryItem.toHistoryDTO(): HistoryDTO {
    return HistoryDTO(
        input_sentence = this.sentence,
        grammar_point = this.grammar.grammar,
        grammar_definition_1 = this.grammar.definition1,
        grammar_definition_2 = this.grammar.definition2,
        grammar_level = this.grammar.level,
        vocabulary_word = this.vocab.word,
        vocabulary_definition = this.vocab.definition,
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


fun HistoryEntity.toUiHistoryItem(): UiHistoryItem {
    return UiHistoryItem(
        id = this.id,
        sentence = this.input_sentence,
        grammar = this.toUiPracticeGrammar(),
        vocab = this.toUiPractiveVocab(),
        isSaved = is_favorited
    )
}

private fun HistoryEntity.toUiPractiveVocab(): UiPracticeVocab {
    return UiPracticeVocab(
        word = this.vocabulary_word,
        definition = this.vocabulary_definition
    )
}

private fun HistoryEntity.toUiPracticeGrammar(): UiPracticeGrammar {
    return UiPracticeGrammar(
        grammar = grammar_point,
        definition1 = grammar_definition_1,
        definition2 = grammar_definition_2,
        level = grammar_level
    )
}

fun List<GrammarLevel>.toUiPracticeGrammarList(): List<UiPracticeGrammarLevel> {
    var list = emptyList<UiPracticeGrammarLevel>()
    for (i in 0 until this.size - 1) {

        list = list.plus(UiPracticeGrammarLevel(
            this[i].title,
            false,
            this[i].grammarList.map { it.toUiPracticeGrammar() }
        ))
    }
    return list

}

fun GrammarPoint.toUiPracticeGrammar(): UiPracticeGrammar {
    return UiPracticeGrammar(
        grammar = this.grammarTitle,
        definition1 = this.grammarDef1,
        definition2 = this.grammarDef2,
        level = this.grammarCategory.toString()
    )
}

fun VocabSet.toUiPracticeVocabList(): List<UiPracticeVocab> {
    return this.cards.map { it.toUiPracticeVocab() }
}

fun VocabularyCard.toUiPracticeVocab(): UiPracticeVocab {
    return UiPracticeVocab(
        word = this.word,
        definition = this.definition
    )
}