package core.domain

import com.jaegerapps.malmali.chat.models.ConversationDTO
import com.jaegerapps.malmali.chat.models.ConversationEntity
import com.jaegerapps.malmali.chat.models.TopicPromptDTO
import com.jaegerapps.malmali.information.models.GradedSentenceDTO

interface ChatGptApi {
    suspend fun initiateConversation(topic: TopicPromptDTO): ConversationDTO
    suspend fun continueConversation(conversation: List<ConversationEntity>): List<ConversationDTO>
    suspend fun gradePractice(sentence: String): GradedSentenceDTO
}