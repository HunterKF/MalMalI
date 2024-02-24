package com.jaegerapps.malmali.chat.models

import kotlinx.serialization.Serializable

@Serializable
class ConversationDTO(
    //This is received from gpt
    val role: String,
    val content: String
)