package core.data.gpt

import co.touchlab.kermit.Logger
import com.jaegerapps.malmali.BuildKonfig
import com.jaegerapps.malmali.chat.models.ConversationDTO
import com.jaegerapps.malmali.chat.models.ConversationEntity
import com.jaegerapps.malmali.chat.models.TopicPromptDTO
import com.jaegerapps.malmali.information.models.GradedSentenceDTO
import core.Knower
import core.Knower.d
import core.data.gpt.mappers.toJsonWithTopic
import core.domain.ChatGptApi
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ChatGptApiImpl(private val client: HttpClient) : ChatGptApi {
    override suspend fun initiateConversation(topic: TopicPromptDTO, userName: String): ConversationDTO {
        val request = toJsonWithTopic(topic, userName)
        val response = createPostRequest(request)
        Knower.d("From initiate conversation", "Here is the response: ${response.bodyAsText()}")
        return ConversationDTO(id = 0, role = "system", content = response.bodyAsText())
    }

    override suspend fun continueConversation(conversation: List<ConversationEntity>): List<ConversationDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun gradePractice(sentence: String): GradedSentenceDTO {
        TODO("Not yet implemented")
    }

    @OptIn(InternalAPI::class)
    override suspend fun createPostRequest(prompt: String): HttpResponse {
        val apiKey = BuildKonfig.GPT_API_KEY // Replace with your actual API key
        val url = "https://api.openai.com/v1/chat/completions"
        return client.post(url) {
            header("Authorization", "Bearer $apiKey")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(
                """
            {
              "model": "gpt-3.5-turbo",
              "temperature": 0.7,
              "messages": $prompt
            }
        """.trimIndent()
            )
        }
    }
}