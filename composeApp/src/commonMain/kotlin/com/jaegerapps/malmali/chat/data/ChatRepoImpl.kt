package com.jaegerapps.malmali.chat.data

import com.jaegerapps.malmali.chat.domain.ChatRepo
import com.jaegerapps.malmali.chat.mappers.toTopicPrompt
import com.jaegerapps.malmali.chat.models.Conversation
import com.jaegerapps.malmali.chat.models.UiTopicPrompt
import com.jaegerapps.malmali.chat.models.TopicPromptDTO
import core.util.Resource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.postgrest

class ChatRepoImpl(private val client: SupabaseClient): ChatRepo {
    override suspend fun getTopics(): Resource<List<UiTopicPrompt>> {
        return try {
            val result = client.postgrest.from("topics").select {

            }.decodeAs<List<TopicPromptDTO>>().map {
                it.toTopicPrompt()
            }
            Resource.Success(result)
        } catch (e: RestException) {
            Resource.Error(Throwable(message = e.message))

        }
    }

    override suspend fun sendConversation(conversation: Conversation): Resource<Conversation> {
        TODO("Not yet implemented")
    }
}