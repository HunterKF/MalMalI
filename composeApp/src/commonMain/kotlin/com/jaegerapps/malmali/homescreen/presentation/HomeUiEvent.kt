package com.jaegerapps.malmali.homescreen.presentation

sealed interface HomeUiEvent {
    data class CreateUser(val name: String): HomeUiEvent
    data object GetUserInfo: HomeUiEvent

}