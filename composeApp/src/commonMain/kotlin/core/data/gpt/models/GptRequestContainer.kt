package core.data.gpt.models

import kotlinx.serialization.Serializable

@Serializable
data class GptRequestContainer(
    val model: String = "gpt-3.5-turbo",
    val temperature: Double = 0.7,
    val messages: Array<GptMessageDTO>
)