package com.jaegerapps.malmali.login.domain

import com.jaegerapps.malmali.MR

fun UserEntity.toUserData(): UserData {
    return UserData(
        nickname = user_nickname,
        email = user_email,
        id = user_id,
        experience = user_experience,
        currentLevel = 1,
        icon = MR.images.cat_icon,
        achievements = user_achievements.toList(),
    )
}