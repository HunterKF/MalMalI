package com.jaegerapps.malmali.vocabulary.models

import com.jaegerapps.malmali.components.models.IconResource
import dev.icerock.moko.resources.ImageResource


data class VocabSet(
    val setId: Int,
    val title: String,
    val icon: IconResource,
    val isPublic: Boolean,
    val tags: List<String> = emptyList(),
    val dateCreated: String,
    val cards: List<VocabularyCard> = emptyList()
)

data class VocabularyCard(
    val word: String,
    val definition: String
)
