package com.jaegerapps.malmali.chat.mappers

import com.jaegerapps.malmali.chat.models.ConversationDTO
import com.jaegerapps.malmali.chat.models.ConversationEntity
import com.jaegerapps.malmali.chat.models.ConversationUi

fun ConversationDTO.toConversationUi(): ConversationUi {
    return ConversationUi(
        id = id,
        role = role,
        content = content,
        selected = false
    )
}

fun ConversationUi.toConversationEntity(): ConversationEntity {
    return ConversationEntity(
        id, role, content
    )
}