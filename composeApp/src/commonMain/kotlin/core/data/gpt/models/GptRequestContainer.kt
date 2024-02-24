package core.data.gpt.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GptRequestContainer(
    @SerialName("model") val model: String = "gpt-3.5-turbo",
    @SerialName("temperature") val temperature: Double = 0.7,
    @SerialName("messages") val messages: Array<GptMessageDTO>
)