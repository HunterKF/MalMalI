package com.jaegerapps.travelplanner.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDto(
    @SerialName("id")
    val id: String,
    @SerialName("choices")
    val choices: Array<ChoicesDto?>,
)


