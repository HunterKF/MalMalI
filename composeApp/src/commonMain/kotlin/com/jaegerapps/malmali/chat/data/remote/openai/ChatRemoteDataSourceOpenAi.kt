package com.jaegerapps.malmali.chat.data.remote.openai

import com.jaegerapps.malmali.chat.data.models.ConversationDTO
import com.jaegerapps.malmali.chat.domain.models.UiTopicPrompt
import core.util.Resource

interface ChatRemoteDataSourceOpenAi {
    suspend fun getTopics(): Resource<List<UiTopicPrompt>>
    suspend fun initializeChat(topicPrompt: UiTopicPrompt, userName: String): ConversationDTO
    suspend fun continueChat(newChat: String, history: List<ConversationDTO>): ConversationDTO

}