package core.domain

import com.jaegerapps.malmali.chat.models.ConversationDTO
import com.jaegerapps.malmali.chat.models.ConversationEntity
import com.jaegerapps.malmali.chat.models.TopicPromptDTO
import com.jaegerapps.malmali.information.models.GradedSentenceDTO
import core.util.Resource
import io.ktor.client.statement.HttpResponse

interface ChatGptApi {
    suspend fun initiateConversation(topic: TopicPromptDTO, userName: String): Resource<ConversationDTO>
    suspend fun continueConversation(conversation: List<ConversationEntity>): Resource<ConversationDTO>
    suspend fun gradePractice(sentence: String): Resource<GradedSentenceDTO>
    suspend fun createPostRequest(prompt: String): HttpResponse
}