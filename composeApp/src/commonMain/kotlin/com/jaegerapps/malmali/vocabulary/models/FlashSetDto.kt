package com.jaegerapps.malmali.vocabulary.models

import kotlinx.serialization.Serializable

@Serializable
data class FlashSetDto(
    val tags: Array<String>,
    val is_public: Boolean,
    val subscribed_users: Array<String>,
    val set_title: String,
    val set_icon: String,
    val vocabulary_word: Array<String>,
    val vocabulary_definition: Array<String>
)
