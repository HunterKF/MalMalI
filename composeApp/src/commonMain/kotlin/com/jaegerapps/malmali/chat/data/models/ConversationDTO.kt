package com.jaegerapps.malmali.chat.data.models

import kotlinx.serialization.Serializable

@Serializable
class ConversationDTO(
    //This is received from gpt
    val role: String,
    val content: String
)