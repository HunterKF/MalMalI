package com.jaegerapps.malmali.chat.presentation.home

import com.jaegerapps.malmali.chat.domain.models.UiTopicPrompt

data class ChatHomeUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val selectedTopic: UiTopicPrompt? = null,
    val topics: List<UiTopicPrompt> = emptyList()
)
