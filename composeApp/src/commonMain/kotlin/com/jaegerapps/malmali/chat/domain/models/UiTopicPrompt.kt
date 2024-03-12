package com.jaegerapps.malmali.chat.domain.models

import com.jaegerapps.malmali.common.models.IconResource

data class UiTopicPrompt(
    val title: String,
    val background: String,
    val icon: IconResource
)