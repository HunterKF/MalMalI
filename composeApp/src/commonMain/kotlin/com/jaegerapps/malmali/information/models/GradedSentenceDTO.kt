package com.jaegerapps.malmali.information.models

data class GradedSentenceDTO(
    val originalSentence: String,
    val correctedSentence: String?,
    val teacherNotes: String
)
