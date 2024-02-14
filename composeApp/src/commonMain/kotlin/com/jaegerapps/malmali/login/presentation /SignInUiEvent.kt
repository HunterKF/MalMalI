package com.jaegerapps.malmali.login.presentation

sealed interface SignInUiEvent {
    data class ChangeEmailValue(val value: String): SignInUiEvent
    data class ChangePasswordValue(val value: String): SignInUiEvent
    data class ChangeRetypePasswordValue(val value: String): SignInUiEvent
    data object CreateAccountWithEmail : SignInUiEvent
    data object SignInWithEmail : SignInUiEvent
    data object SignInWithGmailSuccess: SignInUiEvent
    data class OnError(val signInError: SignInError) : SignInUiEvent
    data object ClearError : SignInUiEvent
    data object ToggleMode : SignInUiEvent
}