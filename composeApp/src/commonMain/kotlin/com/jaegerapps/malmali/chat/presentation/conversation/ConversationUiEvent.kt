package com.jaegerapps.malmali.chat.presentation.conversation

sealed interface ConversationUiEvent {
    data class OnTextChange(val value: String) : ConversationUiEvent
    data object SendChant: ConversationUiEvent
    data object ErrorClear: ConversationUiEvent
    data class OnNavigate(val route: String): ConversationUiEvent

}