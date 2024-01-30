package com.jaegerapps.malmali.login.data

import kotlinx.serialization.Serializable

//This is what I use to send to the database
@Serializable
data class UserDTO(
    val user_nickname: String,
    val user_email: String,
    val user_id: String,
    val user_experience: Int,
    val user_icon: String,
    val user_achievements: Array<String>
)

