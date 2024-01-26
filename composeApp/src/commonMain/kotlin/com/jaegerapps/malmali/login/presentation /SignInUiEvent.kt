package com.jaegerapps.malmali.login.presentation

sealed interface SignInUiEvent {
    data class ChangeEmailValue(val value: String): SignInUiEvent
    data class ChangePasswordValue(val value: String): SignInUiEvent
    data class ChangeRetypePasswordValue(val value: String): SignInUiEvent
    data object CreateAccountWithEmail : SignInUiEvent
    data class SignInWithGmailSuccess(val email: String?, val id: String?) : SignInUiEvent
    data class OnError(val signInError: SignInError) : SignInUiEvent
    data object ClearError : SignInUiEvent
    data object ToggleMode : SignInUiEvent
}