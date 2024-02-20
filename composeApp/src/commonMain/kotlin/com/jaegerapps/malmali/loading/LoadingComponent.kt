package com.jaegerapps.malmali.loading

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnResume
import com.jaegerapps.malmali.RootComponent
import com.jaegerapps.malmali.di.AppModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoadingComponent(
    componentContext: ComponentContext
): ComponentContext by componentContext {

    val scope = CoroutineScope(Dispatchers.IO)
    init {
        lifecycle.doOnResume {
            scope.launch {
            }
        }
    }
}