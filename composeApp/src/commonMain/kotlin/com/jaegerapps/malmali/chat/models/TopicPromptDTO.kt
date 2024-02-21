package com.jaegerapps.malmali.chat.models

import kotlinx.serialization.Serializable

@Serializable
class TopicPromptDTO(
    val id: Int,
    val topic_title: String,
    val topic_background: String
)