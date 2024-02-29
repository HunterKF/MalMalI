package com.jaegerapps.malmali.vocabulary.data.models

data class FlashcardEntity(
    val id: Long?,
    val word: String,
    val definition: String,
    //The public id is the linked_set id.
    val linked_set: Long
)
