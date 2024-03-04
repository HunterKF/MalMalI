package core.domain

import com.jaegerapps.malmali.chat.data.models.ConversationDTO
import com.jaegerapps.malmali.chat.data.models.ConversationEntity
import com.jaegerapps.malmali.chat.data.models.TopicPromptDTO
import com.jaegerapps.malmali.information.models.GradedSentenceDTO
import core.util.Resource
import io.ktor.client.statement.HttpResponse

interface ChatGptApi {
    suspend fun initiateConversation(topic: TopicPromptDTO, userName: String): Resource<ConversationDTO>
    suspend fun continueConversation(conversation: List<ConversationEntity>): Resource<ConversationDTO>
    suspend fun gradePractice(sentence: String): Resource<GradedSentenceDTO>
    suspend fun createPostRequest(prompt: String): HttpResponse
}