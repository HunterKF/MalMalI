package com.jaegerapps.travelplanner.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MessageDto(
    @SerialName("role") val role: String,
    @SerialName("content") val content: String,
)
