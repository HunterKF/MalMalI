package com.jaegerapps.malmali.onboarding.intro

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class IntroComponent(
    componentContext: ComponentContext,
    private val onNavigate: () -> Unit
) : ComponentContext by componentContext {

    private val _state = MutableStateFlow(IntroUiState())
    val state = _state

    fun onEvent(event: IntroUiEvents) {
        when (event) {
            IntroUiEvents.NextPage -> {
                val newPageCount = _state.value.currentPage + 1
                if (newPageCount < 5) {
                    _state.update {
                        it.copy(
                            currentPage = newPageCount
                        )
                    }
                } else {
                    onNavigate()
                }
            }
            IntroUiEvents.SkipPage -> {
                val newPageCount = _state.value.currentPage + 1
                if (newPageCount < 5) {
                    _state.update {
                        it.copy(
                            currentPage = newPageCount
                        )
                    }
                } else {
                    onNavigate()
                }
            }

        }
    }

}