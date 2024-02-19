package com.jaegerapps.malmali.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.jaegerapps.malmali.login.domain.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class HomeScreenComponent(
    componentContext: ComponentContext,
    private val onNavigate: (String) -> Unit,
    user: UserData,
) : ComponentContext by componentContext {

    /*TODO - Eventually will have to make a loading thing so we can load information
    *  from the online database*/
    private val scope = CoroutineScope(Dispatchers.IO)
    private val _state = MutableStateFlow(HomeUiState())
    val state = _state

    init {
        lifecycle.doOnCreate {
            _state.update {
                it.copy(
                    userName = user.nickname,
                    icon = user.icon,
                    userExperience = user.experience.toLong(),
                    currentLevel = user.currentLevel,
                    loading = false
                )
            }
        }
    }

    fun onEvent(event: HomeScreenUiEvent) {
        when (event) {
            is HomeScreenUiEvent.OnNavigate -> {
                onNavigate(event.route)
            }
        }
    }
}