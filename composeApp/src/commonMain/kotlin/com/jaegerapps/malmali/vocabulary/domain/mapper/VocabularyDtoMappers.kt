package com.jaegerapps.malmali.vocabulary.domain.mapper

import com.jaegerapps.malmali.components.models.IconResource
import com.jaegerapps.malmali.vocabulary.data.models.VocabSetDTO
import com.jaegerapps.malmali.vocabulary.data.models.VocabSetDTOWithoutData
import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel
import com.jaegerapps.malmali.vocabulary.domain.models.VocabularyCardModel

fun VocabSetModel.toVocabSetDTO(): VocabSetDTO {
    return VocabSetDTO(
        id = remoteId?.toInt(),
        tags = tags.toTypedArray(),
        is_public = isPublic,
        subscribed_users = emptyArray(),
        author_id = null,
        created_at = dateCreated,
        set_title = title,
        set_icon = icon.tag,
        vocabulary_word = cards.map { it.word }.toTypedArray(),
        vocabulary_definition = cards.map { it.definition }.toTypedArray()
    )
}
fun VocabSetModel.toVocabSetDTOWithoutData(): VocabSetDTOWithoutData {
    return VocabSetDTOWithoutData(
        tags = tags.toTypedArray(),
        is_public = isPublic,
        subscribed_users = emptyArray(),
        set_title = title,
        set_icon = icon.tag,
        vocabulary_word = cards.map { it.word }.toTypedArray(),
        vocabulary_definition = cards.map { it.definition }.toTypedArray()
    )
}

fun VocabSetDTO.toVocabSetModel(isAuthor: Boolean): VocabSetModel {
    return VocabSetModel(
        localId = null,
        remoteId = id,
        title = set_title,
        icon = IconResource.resourceFromTag(set_icon),
        isAuthor = isAuthor,
        isPublic = is_public,
        tags = tags.toList(),
        dateCreated = created_at,
        cards = createVocabularyCardModel(vocabulary_word, vocabulary_definition)
    )
}

private fun createVocabularyCardModel(
    word: Array<String>,
    definition: Array<String>,
): List<VocabularyCardModel> {
    var list = emptyList<VocabularyCardModel>()
    var index = 0
    while (index < word.size) {
        list = list.plus(
            VocabularyCardModel(
                uiId = index,
                word = word[index],
                definition = definition[index],
                dbId = null
            )
        )
        index++
    }
    return list
}