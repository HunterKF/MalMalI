package com.jaegerapps.malmali.chat.presentation

import com.jaegerapps.malmali.chat.models.UiTopicPrompt

data class ChatHomeUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val selectedTopic: UiTopicPrompt? = null,
    val topics: List<UiTopicPrompt> = emptyList()
)
