package com.jaegerapps.malmali.onboarding.personalization

import com.arkivanov.decompose.ComponentContext
import core.domain.SettingFunctions
import core.domain.SupabaseUserFunctions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PersonalizationComponent(
    componentContext: ComponentContext,
    private val onNavigate: () -> Unit,
    private val handleUser: SupabaseUserFunctions,
    private val settings: SettingFunctions,
) : ComponentContext by componentContext {


    private val _state = MutableStateFlow(PersonalizationUiState())
    val scope = CoroutineScope(Dispatchers.IO)
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
                    selectedIcon = event.iconResource,
                    selectIconPopUp = false
                )
            }
            PersonalizationUiEvents.OnNavigate -> {
                if (state.value.nickname.isBlank()) {
                    _state.update {
                        it.copy(
                            error = "Please check your username."
                        )
                    }
                } else {
                    try {
                        scope.launch {
                            async {
                                handleUser.updateUserName(_state.value.nickname)
                                handleUser.updateUserIcon(_state.value.selectedIcon.tag)
                                settings.updateUserName(_state.value.nickname)
                                settings.updateUserIcon(_state.value.selectedIcon.tag)
                                settings.changeOnboardingBoolean()
                            }.await()
                        }
                        onNavigate()

                    } catch (e: Exception) {
                        println("An error occurred: $e")
                    }
                }
            }
            is PersonalizationUiEvents.OnNicknameChange -> _state.update {
                it.copy(
                    nickname = event.value
                )
            }

            PersonalizationUiEvents.ToggleIconSelection -> {
                _state.update {
                    it.copy(
                        selectIconPopUp = !it.selectIconPopUp
                    )
                }
            }

        }
    }
}