package com.jaegerapps.malmali.chat.conversation.presentation

sealed interface ConversationUiEvent {
    data class OnTextChange(val value: String) : ConversationUiEvent
    data object SendChant: ConversationUiEvent
    data object ErrorClear: ConversationUiEvent
    data class OnNavigate(val route: String): ConversationUiEvent

}