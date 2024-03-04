package com.jaegerapps.malmali.chat.domain.mappers

import com.jaegerapps.malmali.chat.data.models.ConversationDTO
import com.jaegerapps.malmali.chat.data.models.ConversationEntity
import com.jaegerapps.malmali.chat.domain.models.ConversationUi

fun ConversationDTO.toConversationUi(): ConversationUi {
    return ConversationUi(
        id = 0,
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

fun ConversationEntity.toConversationUi(id: Int): ConversationUi {
    return ConversationUi(
        id = id,
        role = role,
        content = content,
        selected = false
    )
}