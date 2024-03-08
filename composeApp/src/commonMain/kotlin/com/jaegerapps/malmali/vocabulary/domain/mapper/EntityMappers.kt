package com.jaegerapps.malmali.vocabulary.domain.mapper

import com.jaegerapps.malmali.components.models.IconResource
import com.jaegerapps.malmali.composeApp.database.FlashcardSets
import com.jaegerapps.malmali.vocabulary.data.models.FlashcardEntity
import com.jaegerapps.malmali.vocabulary.data.models.SetEntity
import com.jaegerapps.malmali.vocabulary.data.models.VocabSetDTO
import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel
import com.jaegerapps.malmali.vocabulary.domain.models.VocabularyCardModel

/*Entities will only be taken from DTOs.
* This is designed so that the entity is only ever used after a set has been placed in Supabase*/
fun VocabSetDTO.toSetEntity(isAuthor: Boolean): SetEntity {
    return SetEntity(
        set_id = null,
        linked_set = id!!.toLong(),
        set_title = set_title,
        tags = this.tags.joinToString(", "),
        date_created = created_at,
        is_author = if (!isAuthor) 0 else 1,
        is_public = if (!is_public) 0 else 1,
        vocabulary_word = this.vocabulary_word.toSetEntityString(),
        vocabulary_definition = this.vocabulary_definition.toSetEntityString(),
        set_icon = this.set_icon
    )
}

private fun Array<String>.toSetEntityString(): String {
    return this.joinToString("|&|")
}
private fun List<String>.toSetEntityString(): String {
    return this.joinToString("|&|")
}
private fun String.toArrayOfString(): Array<String> {
    return this.split("|&|").toTypedArray()
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


fun toVocabSetModel(setEntity: SetEntity): VocabSetModel {
    return VocabSetModel(
        localId = setEntity.set_id?.toInt(),
        remoteId = setEntity.linked_set.toInt(),
        title = setEntity.set_title,
        icon = IconResource.resourceFromTag(setEntity.set_icon),
        isAuthor = setEntity.is_author == 1L,
        isPublic = setEntity.is_public == 1L,
        tags =setEntity.tags?.split(" ") ?: emptyList() ,
        dateCreated = setEntity.date_created,
        cards = createCards(setEntity.vocabulary_word, setEntity.vocabulary_definition)
    )
}

private fun createCards(words: String, definitions: String): List<VocabularyCardModel> {
    if (words.isBlank() || definitions.isBlank()) {
        return emptyList()
    }
    val splitWords = words.toArrayOfString()
    val splitDefinitions = definitions.toArrayOfString()
    var index = 0
    var list = emptyList<VocabularyCardModel>()
    while (index < splitWords.size) {
        list = list.plus(
            VocabularyCardModel(
                word = splitWords[index],
                definition = splitDefinitions[index]
            )
        )
        index++
    }
    return list
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
        vocabulary_word = vocabulary_word,
        vocabulary_definition = vocabulary_definition,
        set_icon = this.set_icon
    )
}



fun SetEntity.toVocabSet(): VocabSetModel{
    return VocabSetModel(
        localId = this.set_id?.toInt(),
        remoteId = this.linked_set.toInt(),
        title = this.set_title,
        icon = IconResource.resourceFromTag(this.set_icon),
        isAuthor = this.is_author == 1L,
        isPublic = this.is_public == 1L,
        tags =this.tags?.split(" ") ?: emptyList() ,
        dateCreated = this.date_created,
    )
}
fun VocabSetModel.toSetEntity(localId: Long?): SetEntity{
    return SetEntity(
        set_id = localId,
        linked_set = remoteId!!.toLong(),
        set_title = title,
        tags = tags.toString(),
        date_created = dateCreated,
        is_author = if (!isAuthor) 0L else 1L,
        is_public = if (!isPublic) 0L else 1L,
        vocabulary_word = this.cards.map { it.word }.toSetEntityString(),
        vocabulary_definition =this.cards.map { it.definition }.toSetEntityString() ,
        set_icon = icon.tag
    )
}

fun VocabularyCardModel.toFlashcardEntity(remoteId: Long): FlashcardEntity {
    return FlashcardEntity(
        id = null,
        word = word,
        definition = definition,
        linked_set = remoteId
    )
}