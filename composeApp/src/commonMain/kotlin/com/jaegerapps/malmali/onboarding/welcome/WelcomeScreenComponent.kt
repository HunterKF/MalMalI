package com.jaegerapps.malmali.onboarding.welcome

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow

class WelcomeScreenComponent(
    componentContext: ComponentContext,
    onNavigate: () -> Unit
) : ComponentContext by componentContext {

    private val _state = MutableStateFlow(WelcomeUiState())
    val state = _state

    fun onNavigate() {
        onNavigate()
    }
}