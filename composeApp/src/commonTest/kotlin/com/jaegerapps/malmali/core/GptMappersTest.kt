package com.jaegerapps.malmali.core

import com.jaegerapps.malmali.chat.models.ConversationEntity
import com.jaegerapps.malmali.chat.models.TopicPromptDTO
import core.Knower
import core.Knower.d
import core.Knower.e
import core.Knower.t
import core.data.gpt.mappers.fromStringToConversation
import core.data.gpt.mappers.toJsonWithHistory
import core.data.gpt.mappers.toJsonWithTopic
import kotlin.test.Test
import kotlin.test.assertEquals

class GptMappersTest {


    @Test
    fun toJsonWithTopic() {
        val expectedResult ="""{"model":"gpt-3.5-turbo","temperature":0.7,"messages":[{"role":"system","content":"You are a practice partner named 말말이 for a Korean learner. Your practice partner is still learning Korean, at a low intermediate level. Keep your answers and questions level appropriate. You will be the one initiating the conversation, so you need to start the conversation naturally and with a greeting. You will be given a topic to talk about. Only talk about that topic. If the user tries to go off topic, go back to the topic. Only use Korean, do not switch languages."},{"role":"user","content":"오늘의 주제는 취미입니다. 제 이름은 HunterK300라고 합니다."},{"role":"assistant","content":"안녕하세요! 저는 말말이입니다. 만나서 반갑습니다! 혹시 취미가 있으세요?"},{"role":"user","content":"[previousConversation: {assistant: 안녕하세요! 저는 말말이입니다. 만나서 반갑습니다! 혹시 취미가 있으세요?}, newMessage: 안녕하세요, 말말이. 제 취미는 등산이에요."},{"role":"assistant","content":"우와, 진짜? 등산은 엄청 좋죠. 한국에는 산 많이 있으니까 등산하기에 좋아요. 왜 등산을 좋아하세요?"},{"role":"user","content":"[previousConversation: {assistant: 안녕하세요! 저는 말말이입니다. 만나서 반갑습니다! 혹시 취미가 있으세요?, user: 안녕하세요, 말말이. 제 취미는 등산이에요., assistant: 우와, 진짜? 등산은 엄청 좋죠. 한국에는 산 많이 있으니까 등산하기에 좋아요. 왜 등산을 좋아하세요?}, newMessage: 등산은 몸을 위하여 좋잖아요! 말말이는요? 어떤 취미가 좋나요?."},{"role":"user","content":"오늘의 주제는 취미입니다. hobbies입니다. 우리는 Talk about hobbies, how to get started, or share experiences related to hobbies.에 대해 이야기를 하겠습니다. 제 이름은 Tester300라고 합니다."}]}""".trimIndent()
        val topicPrompt = TopicPromptDTO(
            id = 1,
            topic_title = "hobbies",
            topic_background = "Talk about hobbies, how to get started, or share experiences related to hobbies."
        )
        val userName = "Tester300"
        val result = toJsonWithTopic(topic = topicPrompt, userName = userName)
        Knower.d("mappersTest", "Here is the result: $result")

        assertEquals(expectedResult, result)
    }

    @Test
    fun toJsonWithHistoryTest() {
        val expectedResult = """{"model":"gpt-3.5-turbo","temperature":0.7,"messages":[{"role":"assistant","content":"안녕하세요! 저는 말말이입니다. 만나서 반갑습니다! 혹시 취미가 있으세요?"},{"role":"user","content":"[previousConversation: {assistant: 안녕하세요! 저는 말말이입니다. 만나서 반갑습니다! 혹시 취미가 있으세요?}, newMessage: 안녕하세요, 말말이. 제 취미는 등산이에요."},{"role":"assistant","content":"우와, 진짜? 등산은 엄청 좋죠. 한국에는 산 많이 있으니까 등산하기에 좋아요. 왜 등산을 좋아하세요?"},{"role":"user","content":"[previousConversation: {assistant: 안녕하세요! 저는 말말이입니다. 만나서 반갑습니다! 혹시 취미가 있으세요?, user: 안녕하세요, 말말이. 제 취미는 등산이에요., assistant: 우와, 진짜? 등산은 엄청 좋죠. 한국에는 산 많이 있으니까 등산하기에 좋아요. 왜 등산을 좋아하세요?}, newMessage: 등산은 몸을 위하여 좋잖아요! 말말이는요? 어떤 취미가 좋나요?."}]}""".trimIndent()
        val history = listOf<ConversationEntity>(
            ConversationEntity(id = 0, role = "assistant", content = "안녕하세요! 저는 말말이입니다. 만나서 반갑습니다! 혹시 취미가 있으세요?"),
            ConversationEntity(id = 0, role = "user", content = "[previousConversation: {assistant: 안녕하세요! 저는 말말이입니다. 만나서 반갑습니다! 혹시 취미가 있으세요?}, newMessage: 안녕하세요, 말말이. 제 취미는 등산이에요."),
            ConversationEntity(id = 0, role = "assistant", content = "우와, 진짜? 등산은 엄청 좋죠. 한국에는 산 많이 있으니까 등산하기에 좋아요. 왜 등산을 좋아하세요?"),
            ConversationEntity(id = 0, role = "user", content = "[previousConversation: {assistant: 안녕하세요! 저는 말말이입니다. 만나서 반갑습니다! 혹시 취미가 있으세요?, user: 안녕하세요, 말말이. 제 취미는 등산이에요., assistant: 우와, 진짜? 등산은 엄청 좋죠. 한국에는 산 많이 있으니까 등산하기에 좋아요. 왜 등산을 좋아하세요?}, newMessage: 등산은 몸을 위하여 좋잖아요! 말말이는요? 어떤 취미가 좋나요?."),
        )
        val result = toJsonWithHistory(history)
        Knower.t("toJsonWithHistory", "Here is the result: $result")
        assertEquals(expectedResult, result)
    }
    @Test
    fun fromJsonToConversation() {
        val response =
            "{\"id\":\"chatcmpl-8uxIaTzeVevwB0blP14iYD9YoQJNb\",\"object\":\"chat.completion\",\"created\":1708585004,\"model\":\"gpt-3.5-turbo-0125\",\"choices\":[{\"index\":0,\"message\":{\"role\":\"assistant\",\"content\":\"안녕하세요, please님! 취미에 대해 이야기해요. please님은 어떤 취미를 가지고 계세요?\"},\"logprobs\":null,\"finish_reason\":\"stop\"}],\"usage\":{\"prompt_tokens\":517,\"completion_tokens\":41,\"total_tokens\":558},\"system_fingerprint\":\"fp_cbdb91ce3f\"}"
        val result = fromStringToConversation(response)

        Knower.d(
            "fromJsonToConversation",
            "This is the result going from JSON to a string. We should be getting only the response. \n $result"
        )

        assertEquals("안녕하세요, please님! 취미에 대해 이야기해요. please님은 어떤 취미를 가지고 계세요?", result)

    }

}