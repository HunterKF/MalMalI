package com.jaegerapps.malmali.vocabulary.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FlashSetEntity(
    @SerialName("id")val id: Int,
    @SerialName("created_at")val created_at: String,
    @SerialName("tags")val tags: Array<String>,
    @SerialName("is_public")val is_public: Boolean,
    @SerialName("subscribed_users")val subscribed_users: Array<String>,
    @SerialName("author_id")val author_id: String?,
    @SerialName("set_title")val set_title: String,
    @SerialName("set_icon")val set_icon: String,
    @SerialName("vocabulary_word")val vocabulary_word: Array<String>,
    @SerialName("vocabulary_definition")val vocabulary_definition: Array<String>
)
