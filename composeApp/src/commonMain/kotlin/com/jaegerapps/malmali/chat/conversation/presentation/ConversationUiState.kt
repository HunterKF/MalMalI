package com.jaegerapps.malmali.chat.conversation.presentation

import com.jaegerapps.malmali.chat.models.ConversationUi
import com.jaegerapps.malmali.login.domain.UserData

data class ConversationUiState(
    val user: UserData? = null,
    val waitingResponse: Boolean = false,
    val conversation: List<ConversationUi> = emptyList(),
    val text: String = "",
    val error: String? = null
)
