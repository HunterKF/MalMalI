package com.jaegerapps.malmali.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

class ScreenAComponent(
    componentContext: ComponentContext,
    private val onNavigateToScreenB: (String) -> Unit,
    private val onNavigateToCreateSetScreen: () -> Unit,
    private val onNavigateToFlashcardHome: () -> Unit
) : ComponentContext by componentContext {

    private var _text = MutableValue("")
    val text: Value<String> = _text

    fun onEvent(event: ScreenAEvent) {
        when (event) {
            ScreenAEvent.ClickButton -> onNavigateToScreenB(text.value)
            is ScreenAEvent.UpdateText -> {
                _text.value = event.text
            }

            ScreenAEvent.ClickCreateScreen -> onNavigateToCreateSetScreen()
            ScreenAEvent.ClickFlashcardHomeScreen -> onNavigateToFlashcardHome()
        }
    }
}