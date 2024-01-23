package com.jaegerapps.malmali.navigation

import com.arkivanov.decompose.ComponentContext

class ScreenBComponent(
    val text: String,
    componentContext: ComponentContext,
    private val onGoBack: () -> Unit
) : ComponentContext by componentContext {
    //this can replace a viewmodel...?

    fun goBack() {
        onGoBack()
    }
}