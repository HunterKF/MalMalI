package com.jaegerapps.malmali.vocabulary.models

data class FlashcardEntity(
    val id: Long,
    val word: String,
    val meaning: String,
    val memorization_level: Long?,
    val linked_set: Long
)

