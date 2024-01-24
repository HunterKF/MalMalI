package com.jaegerapps.malmali.home

sealed interface HomeScreenUiEvent {
    data class OnNavigate(val route: String): HomeScreenUiEvent

}