package com.jaegerapps.malmali.vocabulary.mapper

import com.jaegerapps.malmali.components.models.IconResource
import com.jaegerapps.malmali.vocabulary.models.FlashSetDto
import com.jaegerapps.malmali.vocabulary.models.FlashSetEntity
import com.jaegerapps.malmali.vocabulary.models.VocabSet
import com.jaegerapps.malmali.vocabulary.models.VocabularyCard

fun VocabSet.toFlashSetDto(userName: Array<String>? = null): FlashSetDto {
    return FlashSetDto(
        tags = this.tags.toTypedArray(),
        is_public = this.isPublic,
        subscribed_users = userName ?: emptyArray(),
        set_title = this.title,
        set_icon = IconResource.tagFromResource(this.icon),
        vocabulary_word = this.cards.map { it.word }.toTypedArray(),
        vocabulary_definition = this.cards.map { it.definition }.toTypedArray()
    )
}

fun FlashSetEntity.toVocabSet(): VocabSet {
    return VocabSet(
        setId = id,
        title = set_title,
        icon = IconResource.resourceFromTag(this.set_icon),
        dateCreated = this.created_at,
        isPublic = this.is_public,
        tags = this.tags.toList(),
        cards = createCards(words = this.vocabulary_word, definitions = this.vocabulary_definition)
    )
}

fun createCards(words: Array<String>, definitions: Array<String>): List<VocabularyCard> {
    var list = emptyList<VocabularyCard>()
    var index = 0
    while (index < words.size && index < definitions.size) {
        list = list.plus(VocabularyCard(word = words[index], definition = definitions[index]))
        index++
    }
    return list
}