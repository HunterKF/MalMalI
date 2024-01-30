package com.jaegerapps.malmali.onboarding.personalization

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class PersonalizationComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext {


    private val _state = MutableStateFlow(PersonalizationUiState())
    val scope = CoroutineScope(Dispatchers.Main)
    val state = _state.stateIn(
        scope,
        SharingStarted.Lazily,
        initialValue = PersonalizationUiState()
    )

    fun onEvent(event: PersonalizationUiEvents) {
        when (event) {
            PersonalizationUiEvents.OnErrorClear -> {
                _state.update {
                    it.copy(
                        error = null
                    )
                }
            }
            is PersonalizationUiEvents.OnIconChange -> _state.update {
                it.copy(
                    selectedIcon = event.iconResource
                )
            }
            PersonalizationUiEvents.OnNavigate -> TODO()
            is PersonalizationUiEvents.OnNicknameChange -> _state.update {
                it.copy(
                    nickname = event.value
                )
            }
        }
    }
}