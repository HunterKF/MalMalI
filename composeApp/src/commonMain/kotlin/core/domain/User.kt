package core.domain

import dev.icerock.moko.resources.ImageResource
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val nickname: String,
    val email: String,
    val authentication: String,
    val experience: Int,
    val currentLevel: Int,
    val icon: Int,
    val achievements: List<String>
)
