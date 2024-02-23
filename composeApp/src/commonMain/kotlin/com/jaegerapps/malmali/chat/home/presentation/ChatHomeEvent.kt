package com.jaegerapps.malmali.chat.home.presentation

import com.jaegerapps.malmali.chat.models.UiTopicPrompt

sealed interface ChatHomeEvent {
    data class SelectTopic(val topic: UiTopicPrompt): ChatHomeEvent
    data class OnNavigate(val route: String): ChatHomeEvent


}