package com.jaegerapps.malmali.homescreen.presentation

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(

): ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()
    val settings: Settings = Settings()
    fun onEvent(event: HomeUiEvent) {
        when(event) {
            is HomeUiEvent.CreateUser -> {
                settings.putString("user_name", event.name)
            }
            HomeUiEvent.GetUserInfo -> {
                val name = settings.getStringOrNull("user_name")
                println("Here is the returned name: $name")
            }
        }
    }
    private fun createUser(name: String) {
        viewModelScope.launch {
        }
    }

    private fun getUserInfo() {
        viewModelScope.launch {

            _state.update {
                it.copy(
                )
            }
        }
    }
}