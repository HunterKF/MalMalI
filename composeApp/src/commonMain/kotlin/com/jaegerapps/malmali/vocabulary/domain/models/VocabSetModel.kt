package com.jaegerapps.malmali.vocabulary.domain.models

import com.jaegerapps.malmali.components.models.IconResource


data class VocabSetModel(
    //set id is the local db
    val localId: Int?,
    //public id is the remote id
    val remoteId: Int?,
    val title: String,
    val icon: IconResource,
    val isAuthor: Boolean,
    val isPublic: Boolean,
    val tags: List<String> = emptyList(),
    val dateCreated: String?,
    val cards: List<VocabularyCardModel> = emptyList()
)
