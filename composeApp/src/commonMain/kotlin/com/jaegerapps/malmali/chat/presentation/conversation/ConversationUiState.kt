package com.jaegerapps.malmali.chat.presentation.conversation

import com.jaegerapps.malmali.chat.domain.models.ConversationUi
import com.jaegerapps.malmali.login.domain.UserData

data class ConversationUiState(
    val user: UserData? = null,
    val waitingResponse: Boolean = false,
    val conversation: List<ConversationUi> = emptyList(),
    val text: String = "",
    val error: String? = null
)
