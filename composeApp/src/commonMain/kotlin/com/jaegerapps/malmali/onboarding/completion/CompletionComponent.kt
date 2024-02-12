package com.jaegerapps.malmali.onboarding.completion

import com.arkivanov.decompose.ComponentContext

class CompletionComponent(
    componentContext: ComponentContext,
    private val onNavigate: () -> Unit,
) : ComponentContext by componentContext {

    fun onEvent(event: CompletionUiEvent) {
        when (event)  {
            CompletionUiEvent.OnNavigate -> {
                onNavigate()
            }
        }
    }
}