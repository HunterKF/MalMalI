package core.data.gpt

import com.jaegerapps.malmali.chat.models.ConversationDTO
import com.jaegerapps.malmali.chat.models.ConversationEntity
import com.jaegerapps.malmali.chat.models.TopicPromptDTO
import com.jaegerapps.malmali.information.models.GradedSentenceDTO
import core.domain.ChatGptApi
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod

class ChatGptApiImpl(private val client: HttpClient): ChatGptApi {
    override suspend fun initiateConversation(topic: TopicPromptDTO): ConversationDTO {
        TODO("Not yet implemented")
        /*
        client.get("https://api.openai.com/v1/chat/completions") {
            headers {
                append(HttpHeaders.ContentType, "application/json")
                append(HttpHeaders.Authorization, "application/json")
            }
        }*/
    }

    override suspend fun continueConversation(conversation: List<ConversationEntity>): List<ConversationDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun gradePractice(sentence: String): GradedSentenceDTO {
        TODO("Not yet implemented")
    }
}