package com.jaegerapps.malmali.vocabulary.data.models

import kotlinx.serialization.Serializable

@Serializable
data class VocabSetDTO(
    val id: Int?,
    val tags: Array<String>,
    val is_public: Boolean,
    val subscribed_users: Array<String>,
    val author_id: String?,
    val created_at: String?,
    val set_title: String,
    val set_icon: String,
    val vocabulary_word: Array<String>,
    val vocabulary_definition: Array<String>
)
@Serializable
data class VocabSetDTOWithoutData(
    val tags: Array<String>,
    val is_public: Boolean,
    val subscribed_users: Array<String>,
    val set_title: String,
    val set_icon: String,
    val vocabulary_word: Array<String>,
    val vocabulary_definition: Array<String>
)
