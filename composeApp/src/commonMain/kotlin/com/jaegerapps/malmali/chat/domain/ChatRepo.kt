package com.jaegerapps.malmali.chat.domain

import com.jaegerapps.malmali.chat.models.ConversationDTO
import com.jaegerapps.malmali.chat.models.ConversationUi
import com.jaegerapps.malmali.chat.models.TopicPromptDTO
import com.jaegerapps.malmali.chat.models.UiTopicPrompt
import core.util.Resource

interface ChatRepo {
    suspend fun getTopics(): Resource<List<UiTopicPrompt>>
    suspend fun sendConversation(
        message: String,
        history: List<ConversationUi>,
    ): Resource<ConversationUi>

    suspend fun startConversation(
        topicPrompt: UiTopicPrompt,
        userName: String,
    ): Resource<ConversationUi>
}