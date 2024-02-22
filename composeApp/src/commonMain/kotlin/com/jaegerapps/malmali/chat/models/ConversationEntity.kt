package com.jaegerapps.malmali.chat.models

import kotlinx.serialization.Serializable

@Serializable
class ConversationEntity (
    //This is sent to gpt
    val id: Int,
    val role: String,
    val content: String
)