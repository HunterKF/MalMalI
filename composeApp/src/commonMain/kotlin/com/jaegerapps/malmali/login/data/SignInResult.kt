package com.jaegerapps.malmali.login.data

import com.jaegerapps.malmali.login.domain.UserData

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)