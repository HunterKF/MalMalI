package core.domain.supabase.flashcards.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SetEntity(
    @SerialName("id") val id: Int,
    @SerialName("created_at") val created_at: String,
    @SerialName("tags") val tags: Array<String>,
    @SerialName("linked_set") val linked_set: String,
    @SerialName("is_public") val is_public: Boolean,
    @SerialName("subscribed_users") val subscribed_users: Array<String>,
    @SerialName("author_id") val author_id: String,
)
