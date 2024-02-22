package com.jaegerapps.travelplanner.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChoicesDto(
    val message: MessageDto,
    @SerialName("finish_reason")
    val finish_reason: String,
    @SerialName("index")
    val index: Int
)
