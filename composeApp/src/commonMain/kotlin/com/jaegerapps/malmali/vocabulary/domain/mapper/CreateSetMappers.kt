package com.jaegerapps.malmali.vocabulary.domain.mapper

import com.jaegerapps.malmali.vocabulary.domain.models.VocabularyCardModel
import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel

fun VocabSetModel.toUiFlashcard(): List<VocabularyCardModel> {
    val list = (this.cards.indices).map {
        this.cards[it].toUiFlashcard(it)
    }
    return list
}

fun VocabularyCardModel.toUiFlashcard(id: Int): VocabularyCardModel {
    return UiFlashcard(
        uiId = id,
        word = this.word,
        def = this.definition,
        error = false
    )
}

fun List<VocabularyCardModel>.toVocabCardList(): List<VocabularyCardModel> {
    val list = (this.indices).map {
        this[it].toVocabularyCard()
    }
    return list
}

fun VocabularyCardModel.toVocabularyCard(): VocabularyCardModel {
    return VocabularyCardModel(
        word = this.word,
        definition = this.def,
    )
}