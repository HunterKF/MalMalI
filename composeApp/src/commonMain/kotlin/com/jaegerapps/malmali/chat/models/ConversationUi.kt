package com.jaegerapps.malmali.chat.models

data class ConversationUi(
    val id: Int,
    val role: String,
    val content: String,
    val selected: Boolean
)
