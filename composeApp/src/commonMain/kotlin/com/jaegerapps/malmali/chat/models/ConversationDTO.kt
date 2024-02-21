package com.jaegerapps.malmali.chat.models

import kotlinx.serialization.Serializable

@Serializable
class ConversationDTO(
    val id: Int,
    val role: String,
    val content: String
)