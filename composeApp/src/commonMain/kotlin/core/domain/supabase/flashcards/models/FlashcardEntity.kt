package core.domain.supabase.flashcards.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FlashcardEntity(
    @SerialName("id") val id: Long,
    @SerialName("linked_set") val linked_set: String,
    @SerialName("word") val word: String,
    @SerialName("definition") val definition: String,
)

