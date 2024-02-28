package com.jaegerapps.malmali.vocabulary.create_set.domain.mapper

import com.jaegerapps.malmali.vocabulary.models.UiFlashcard
import com.jaegerapps.malmali.vocabulary.models.VocabSet
import com.jaegerapps.malmali.vocabulary.models.VocabularyCard

fun VocabSet.toUiFlashcard(): List<UiFlashcard> {
    val list = (this.cards.indices).map {
        this.cards[it].toUiFlashcard(it)
    }
    return list
}

fun VocabularyCard.toUiFlashcard(id: Int): UiFlashcard {
    return UiFlashcard(
        uiId = id,
        word = this.word,
        def = this.definition,
        error = false
    )
}

fun List<UiFlashcard>.toVocabCardList(): List<VocabularyCard> {
    val list = (this.indices).map {
        this[it].toVocabularyCard()
    }
    return list
}

fun UiFlashcard.toVocabularyCard(): VocabularyCard {
    return VocabularyCard(
        word = this.word,
        definition = this.def,
    )
}