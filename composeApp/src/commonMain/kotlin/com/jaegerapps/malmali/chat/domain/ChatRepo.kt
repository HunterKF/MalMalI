package com.jaegerapps.malmali.chat.domain

import com.jaegerapps.malmali.chat.models.Conversation
import com.jaegerapps.malmali.chat.models.UiTopicPrompt
import core.util.Resource

interface ChatRepo {
    suspend fun getTopics(): Resource<List<UiTopicPrompt>>
    suspend fun sendConversation(conversation: Conversation): Resource<Conversation>
}