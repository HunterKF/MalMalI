package com.jaegerapps.malmali.chat.data

import com.jaegerapps.malmali.chat.domain.ChatRepo
import com.jaegerapps.malmali.chat.mappers.toConversationEntity
import com.jaegerapps.malmali.chat.mappers.toConversationUi
import com.jaegerapps.malmali.chat.mappers.toTopicPrompt
import com.jaegerapps.malmali.chat.mappers.toTopicPromptDTO
import com.jaegerapps.malmali.chat.models.ConversationUi
import com.jaegerapps.malmali.chat.models.UiTopicPrompt
import com.jaegerapps.malmali.chat.models.TopicPromptDTO
import core.Knower
import core.Knower.e
import core.domain.ChatGptApi
import core.util.Resource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.postgrest

class ChatRepoImpl(private val client: SupabaseClient, private val api: ChatGptApi) : ChatRepo {
    override suspend fun getTopics(): Resource<List<UiTopicPrompt>> {
        return try {
            val result = client.postgrest.from("topic_prompts").select {

            }.decodeAs<List<TopicPromptDTO>>().map {
                it.toTopicPrompt()
            }
            Resource.Success(result)
        } catch (e: RestException) {
            Resource.Error(Throwable(message = e.message))

        }
    }

    override suspend fun sendConversation(
        message: String,
        history: List<ConversationUi>,
    ): Resource<ConversationUi> {
        val list = history.plus(
            ConversationUi(
                id = history.size + 1,
                role = "user",
                content = message,
                selected = false
            )
        ).map { it.toConversationEntity() }
        return try {
            val result = api.continueConversation(list)
            if (result.data != null ) {
                Resource.Success(result.data.toConversationUi())
            } else {
                Resource.Error(Throwable(message = "Unknown error"))

            }
        } catch(e: Exception) {
            Knower.e("sendConversation", "An error has occurred here. ${e.message}")
            Resource.Error(e)
        }
    }

    override suspend fun startConversation(
        topicPrompt: UiTopicPrompt,
        userName: String,
    ): Resource<ConversationUi> {
        return try {
            val result =
                api.initiateConversation(topicPrompt.toTopicPromptDTO(), userName = userName)
            if (result.data != null ) {
                Resource.Success(result.data.toConversationUi())
            } else {
                Resource.Error(Throwable(message = "Unknown error"))

            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}