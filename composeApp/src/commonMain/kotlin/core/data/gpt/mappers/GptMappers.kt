package core.data.gpt.mappers

import com.jaegerapps.malmali.chat.models.TopicPromptDTO
import com.jaegerapps.travelplanner.data.remote.dto.ResponseDto
import core.Knower
import core.Knower.d
import core.data.gpt.models.GptMessageDTO
import core.data.gpt.models.GptRequestContainer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun toJsonWithTopic(topic: TopicPromptDTO, userName: String): String {
    val initialPromptList = arrayOf(
        GptRequestContainer(
            messages = arrayOf(
                GptMessageDTO(
                    role = "system",
                    content = "You are a practice partner named 말말이 for a Korean learner. Your practice partner is still learning Korean, at a low intermediate level. Keep your answers and questions level appropriate. You will be the one initiating the conversation, so you need to start the conversation naturally and with a greeting. You will be given a topic to talk about. Only talk about that topic. If the user tries to go off topic, go back to the topic. Only use Korean, do not switch languages."
                ),
                GptMessageDTO(
                    role = "user",
                    content = "오늘의 주제는 취미입니다. 제 이름은 HunterK300라고 합니다."
                ),
                GptMessageDTO(
                    role = "assistant",
                    content = "안녕하세요! 저는 말말이입니다. 만나서 반갑습니다! 혹시 취미가 있으세요?"
                ),
                GptMessageDTO(
                    role = "user",
                    content = "[previousConversation: {assistant: 안녕하세요! 저는 말말이입니다. 만나서 반갑습니다! 혹시 취미가 있으세요?}, newMessage: 안녕하세요, 말말이. 제 취미는 등산이에요."
                ),
                GptMessageDTO(
                    role = "assistant",
                    content = "우와, 진짜? 등산은 엄청 좋죠. 한국에는 산 많이 있으니까 등산하기에 좋아요. 왜 등산을 좋아하세요?"
                ),
                GptMessageDTO(
                    role = "user",
                    content = "[previousConversation: {assistant: 안녕하세요! 저는 말말이입니다. 만나서 반갑습니다! 혹시 취미가 있으세요?, user: 안녕하세요, 말말이. 제 취미는 등산이에요., assistant: 우와, 진짜? 등산은 엄청 좋죠. 한국에는 산 많이 있으니까 등산하기에 좋아요. 왜 등산을 좋아하세요?}, newMessage: 등산은 몸을 위하여 좋잖아요! 말말이는요? 어떤 취미가 좋나요?."
                ),
                GptMessageDTO(
                    role = "user",
                    content = "오늘의 주제는 취미입니다. ${topic.topic_title}입니다. 우리는 ${topic.topic_background}에 대해 이야기를 하겠습니다. 제 이름은 ${userName}라고 합니다."
                )
            )
        )

    )
    val json = Json.encodeToString(initialPromptList)
    Knower.d("toJsonWithTopic", "Converting to Json, testing: $json")


    return json
}


fun fromStringToConversation(response: String): String? {
    val json = Json {
        ignoreUnknownKeys = true
    }
    val decoded = json.decodeFromString<ResponseDto>(response)
    return decoded.choices[0]?.message?.content
}