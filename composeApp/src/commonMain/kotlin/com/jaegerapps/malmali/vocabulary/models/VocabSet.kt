package com.jaegerapps.malmali.vocabulary.models

import com.jaegerapps.malmali.vocabulary.create_set.presentation.SetMode
import dev.icerock.moko.resources.ImageResource


data class VocabSet(
    val title: String,
    val icon: ImageResource,
    val setId: Long?,
    val expanded: Boolean,
    val isPrivate: SetMode,
    val dateCreated: Long

)
