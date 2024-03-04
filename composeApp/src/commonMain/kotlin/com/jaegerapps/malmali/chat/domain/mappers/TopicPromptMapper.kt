package com.jaegerapps.malmali.chat.domain.mappers

import com.jaegerapps.malmali.chat.domain.models.UiTopicPrompt
import com.jaegerapps.malmali.chat.data.models.TopicPromptDTO
import com.jaegerapps.malmali.components.models.IconResource

fun TopicPromptDTO.toTopicPrompt(): UiTopicPrompt {
    return UiTopicPrompt(
        title = this.topic_title,
        background = this.topic_background,
        icon = IconResource.resourceFromTag(this.topic_title)
    )
}

fun UiTopicPrompt.toTopicPromptDTO(): TopicPromptDTO {
    return TopicPromptDTO(
        id = 0,
        title,
        background
    )
}