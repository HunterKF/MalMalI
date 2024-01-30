package com.jaegerapps.malmali.login.domain

import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.components.models.IconResource

fun UserEntity.toUserData(): UserData {
    return UserData(
        nickname = user_nickname,
        email = user_email,
        id = user_id,
        experience = user_experience,
        currentLevel = 1,
        icon = IconResource.resourceFromTag(this.user_icon),
        achievements = user_achievements.toList(),
    )
}