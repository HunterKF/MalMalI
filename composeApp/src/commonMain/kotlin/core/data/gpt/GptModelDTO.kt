package core.data.gpt

data class GptModelDTO(
    val model: String = "gpt-3.5-turbo",
    val messages: Array<GptDTO>
)