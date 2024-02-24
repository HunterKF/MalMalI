package core.data.gpt

import co.touchlab.kermit.Logger
import com.jaegerapps.malmali.BuildKonfig
import com.jaegerapps.malmali.chat.models.ConversationDTO
import com.jaegerapps.malmali.chat.models.ConversationEntity
import com.jaegerapps.malmali.chat.models.TopicPromptDTO
import com.jaegerapps.malmali.information.models.GradedSentenceDTO
import core.Knower
import core.Knower.d
import core.Knower.e
import core.data.gpt.mappers.fromStringToConversation
import core.data.gpt.mappers.toJsonWithHistory
import core.data.gpt.mappers.toJsonWithTopic
import core.domain.ChatGptApi
import core.util.Resource
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
    override suspend fun initiateConversation(
        topic: TopicPromptDTO,
        userName: String,
    ): Resource<ConversationDTO> {
        return try {
            val request = toJsonWithTopic(topic, userName)
            val response = createPostRequest(request)
            Knower.d("From initiate conversation", "Here is the response: ${response.bodyAsText()}")
            val message = fromStringToConversation(response.bodyAsText())
            if (message != null) {
                Resource.Success(ConversationDTO(role = "system", content = message))
            } else {
                Resource.Error(Throwable(message = "An error occurred."))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun continueConversation(conversation: List<ConversationEntity>): Resource<ConversationDTO> {
        return try {
            val request = toJsonWithHistory(conversation)
            val response = createPostRequest(request)
            val message = fromStringToConversation(response.bodyAsText())
            Knower.d("continueConversation", "Returning now. Here is the message: ${message}")
            if (message != null) {
                Resource.Success(ConversationDTO(role = "assistant", content = message))
            } else {
                Knower.e("continueConversation", "Error occurred, the result was null.")

                Resource.Error(Throwable(message = "An error occurred."))
            }
        } catch (e: Exception) {
            Knower.e("continueConversation", "Error occurred, exception caught: ${e.message}")

            Resource.Error(e)
        }
    }

    override suspend fun gradePractice(sentence: String): Resource<GradedSentenceDTO> {
        TODO("Not yet implemented")
    }

    @OptIn(InternalAPI::class)
    override suspend fun createPostRequest(prompt: String): HttpResponse {
        val apiKey = BuildKonfig.GPT_API_KEY
        val url = "https://api.openai.com/v1/chat/completions"
        return client.post(url) {
            header("Authorization", "Bearer $apiKey")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(prompt)
        }
    }
}