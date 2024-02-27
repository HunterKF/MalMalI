package core.domain.supabase.flashcards.models

import kotlinx.serialization.Serializable

@Serializable
data class SetDto(
    val id: Int,
    val linked_set: String,
    val set_title: String,
    val tags: List<String> = emptyList(),
    val is_public: Boolean
)
