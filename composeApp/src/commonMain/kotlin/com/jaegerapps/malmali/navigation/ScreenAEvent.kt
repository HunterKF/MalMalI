package com.jaegerapps.malmali.navigation

sealed interface ScreenAEvent {
    data object ClickButton: ScreenAEvent
    data object ClickCreateScreen: ScreenAEvent
    data object ClickFlashcardHomeScreen: ScreenAEvent
    data class UpdateText(val text: String): ScreenAEvent
}