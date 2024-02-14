package com.jaegerapps.malmali.login.presentation

data class SignInState(
    val email: String = "",
    val password: String = "",
    val retypePassword: String = "",
    val isLoading: Boolean = false,
    val error: SignInError? = null,
    val loginSuccess: Boolean = false,
    val mode: SignInMode = SignInMode.ACCOUNT_CREATE
)

enum class SignInMode {
    SIGN_IN,
    ACCOUNT_CREATE
}

enum class SignInError {
    PASSWORD_NOT_SAME,
    PASSWORD_BLANK,
    PASSWORD_TOO_SHORT,
    EMAIL_VALIDATION,
    EMAIL_BLANK,
    NETWORK_ERROR,
    UNKNOWN_ERROR,
    ALREADY_REGISTERED,
    PASSWORD_EMAIL_INVALID,
    EMAIL_NOT_FOUND
}