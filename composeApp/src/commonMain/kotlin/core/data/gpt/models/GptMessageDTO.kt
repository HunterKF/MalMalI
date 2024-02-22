package core.data.gpt.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GptMessageDTO(
    @SerialName("role") val role: String,
    @SerialName("content") val content: String,
)