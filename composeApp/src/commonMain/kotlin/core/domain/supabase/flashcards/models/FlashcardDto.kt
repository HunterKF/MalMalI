package core.domain.supabase.flashcards.models

import kotlinx.serialization.Serializable

@Serializable
data class FlashcardDto(
    val id: Int,
    val linked_set: String,
    val word: String,
    val definition: String
)