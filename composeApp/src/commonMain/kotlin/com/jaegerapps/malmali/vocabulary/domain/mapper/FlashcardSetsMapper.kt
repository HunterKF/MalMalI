package com.jaegerapps.malmali.vocabulary.domain.mapper

import com.jaegerapps.malmali.components.models.IconResource
import com.jaegerapps.malmali.vocabulary.data.models.FlashcardEntity
import com.jaegerapps.malmali.vocabulary.data.models.VocabSetDTO
import com.jaegerapps.malmali.vocabulary.domain.models.FlashSetEntity
import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel
import com.jaegerapps.malmali.vocabulary.domain.models.VocabularyCardModel

fun VocabSetModel.toVocabSetDTO(userName: Array<String>? = null): VocabSetDTO {
    return VocabSetDTO(
        id = this.setId,
        author_id = "",
        tags = this.tags.toTypedArray(),
        is_public = this.isPublic,
        subscribed_users = userName ?: emptyArray(),
        set_title = this.title,
        set_icon = IconResource.tagFromResource(this.icon),
        vocabulary_word = this.cards.map { it.word }.toTypedArray(),
        vocabulary_definition = this.cards.map { it.def }.toTypedArray(),
        created_at = ""
    )
}

fun FlashSetEntity.toVocabSet(): VocabSetModel {
    return VocabSetModel(
        setId = id,
        title = set_title,
        icon = IconResource.resourceFromTag(this.set_icon),
        dateCreated = this.created_at,
        isPublic = this.is_public,
        tags = this.tags.toList(),
        cards = createCards(words = this.vocabulary_word, definitions = this.vocabulary_definition)
    )
}

fun VocabSetModel.toFlashcardEntity(): List<FlashcardEntity> {
    return this.cards.map { it.toFlashcardEntity(this.) }
}

fun VocabularyCardModel.toFlashcardEntity(linkedSet: Long): FlashcardEntity {
    return FlashcardEntity(
        id = dbId,
        word = this.word,
        definition =this.def,
        linked_set = linkedSet

    )
}
fun createCards(words: Array<String>, definitions: Array<String>): List<VocabularyCardModel> {
    var list = emptyList<VocabularyCardModel>()
    var index = 0
    while (index < words.size && index < definitions.size) {
        list = list.plus(VocabularyCardModel(word = words[index], def = definitions[index], error = false))
        index++
    }
    return list
}