package com.jaegerapps.malmali.vocabulary.domain.mapper

import com.jaegerapps.malmali.components.models.IconResource
import com.jaegerapps.malmali.composeApp.database.FlashcardSets
import com.jaegerapps.malmali.composeApp.database.Flashcards
import com.jaegerapps.malmali.vocabulary.data.models.FlashcardEntity
import com.jaegerapps.malmali.vocabulary.data.models.SetEntity
import com.jaegerapps.malmali.vocabulary.data.models.VocabSetDTO
import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel
import com.jaegerapps.malmali.vocabulary.domain.models.VocabularyCardModel

/*Entities will only be taken from DTOs.
* This is designed so that the entity is only ever used after a set has been placed in Supabase*/
fun VocabSetDTO.toVocabSetEntity(isAuthor: Boolean): SetEntity {
    return SetEntity(
        set_id = null,
        linked_set = id!!.toLong(),
        set_title = set_title,
        tags = this.tags.toString(),
        date_created = created_at,
        is_author = if (!isAuthor) 0 else 1,
        is_public = if (!is_public) 0 else 1,
        set_icon = this.set_icon
    )
}

fun VocabSetDTO.toFlashcardEntity(): List<FlashcardEntity> {
    var list = emptyList<FlashcardEntity>()
    var index = 0
    while (index < this.vocabulary_word.size) {
        list = list.plus(
            FlashcardEntity(
                id = null,
                word = vocabulary_word[index],
                definition = vocabulary_definition[index],
                linked_set = this.id!!.toLong()
            )
        )
        index++
    }
    return list
}

fun List<FlashcardEntity>.toVocabularyCardModelList(): List<VocabularyCardModel> {
    return this.map {
        VocabularyCardModel(
            word = it.word,
            def = it.definition,
            dbId = it.id
        )
    }
}
fun toVocabSetModel(setEntity: SetEntity, cards: List<FlashcardEntity>): VocabSetModel {
    return VocabSetModel(
        setId = setEntity.set_id?.toInt(),
        publicId = setEntity.linked_set.toInt(),
        title = setEntity.set_title,
        icon = IconResource.resourceFromTag(setEntity.set_icon),
        isAuthor = setEntity.is_author == 1L,
        isPublic = setEntity.is_public == 1L,
        tags =setEntity.tags?.split(" ") ?: emptyList() ,
        dateCreated = setEntity.date_created,
        cards = cards.toVocabularyCardModelList()
    )
}

fun FlashcardSets.toSetEntity(): SetEntity {
    return SetEntity(
        set_id = set_id,
        linked_set = public_id,
        set_title = set_title,
        tags = this.tags.toString(),
        date_created = date_created,
        is_author = is_author,
        is_public = is_public,
        set_icon = this.set_icon
    )
}

fun List<Flashcards>.toFlashcardEntityList(): List<FlashcardEntity> {
    return this.map {
        FlashcardEntity(
            id = it.id,
            word = it.card_word,
            definition = it.card_definition,
            linked_set = it.linked_set
        )
    }
}