package com.jaegerapps.malmali.login.domain

import kotlinx.serialization.Serializable

//This is what is collected from the database
@Serializable
data class UserEntity(
    val created_at: String,
    val user_nickname: String,
    val user_email: String,
    val user_id: String,
    val user_experience: Int,
    val user_icon: String,
    val user_achievements: Array<String>,
    val user_sets: Array<String>
)
