package com.jaegerapps.malmali.chat.presentation.home

import com.jaegerapps.malmali.chat.domain.models.UiTopicPrompt

sealed interface ChatHomeEvent {
    data class SelectTopic(val topic: UiTopicPrompt): ChatHomeEvent
    data class OnNavigate(val route: String): ChatHomeEvent


}