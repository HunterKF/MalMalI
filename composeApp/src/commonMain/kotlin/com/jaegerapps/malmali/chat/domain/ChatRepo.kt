package com.jaegerapps.malmali.chat.domain

import com.jaegerapps.malmali.chat.models.ConversationUi
import com.jaegerapps.malmali.chat.models.UiTopicPrompt
import core.util.Resource

interface ChatRepo {
    suspend fun getTopics(): Resource<List<UiTopicPrompt>>
    suspend fun sendConversation(conversation: ConversationUi): Resource<ConversationUi>
}