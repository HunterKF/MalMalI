package com.jaegerapps.malmali.login.domain

import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.components.models.IconResource
import com.jaegerapps.malmali.login.data.UserDTO

fun UserEntity.toUserData(): UserData {
    return UserData(
        nickname = user_nickname,
        experience = user_experience,
        currentLevel = 1,
        icon = IconResource.resourceFromTag(this.user_icon),
        achievements = user_achievements.toList(),
        sets = user_sets.toList()
    )
}

fun UserData.toUserDto(): UserDTO {
    return UserDTO(
        user_nickname = nickname,
        user_experience = experience,
        user_current_level = currentLevel,
        user_icon = IconResource.tagFromResource(icon),
        user_achievements = achievements.toTypedArray(),
        user_sets = sets.toTypedArray(),
    )
}