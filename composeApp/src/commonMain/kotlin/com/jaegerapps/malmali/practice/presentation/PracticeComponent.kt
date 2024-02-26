package com.jaegerapps.malmali.practice.presentation

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class PracticeComponent(
    private val onNavigate: (String) -> Unit,
    componentContext: ComponentContext,
) : ComponentContext by componentContext {

    private val _state = MutableStateFlow(PracticeUiState())
    val state = _state

    val scope = CoroutineScope(Dispatchers.IO)

    fun onEvent(event: PracticeUiEvent) {
        when (event) {
            is PracticeUiEvent.OnNavigate -> onNavigate(event.route)
            is PracticeUiEvent.OnValueChanged -> {
                _state.update {
                    it.copy(
                        text = event.value
                    )
                }
            }
            PracticeUiEvent.SavePractice -> TODO()
            PracticeUiEvent.ToggleGrammarDropDown -> {
                _state.update {
                    it.copy(
                        isGrammarExpanded = !it.isGrammarExpanded,
                        isVocabularyExpand = false
                    )
                }
            }
            PracticeUiEvent.ToggleVocabDropDown -> {
                _state.update {
                    it.copy(
                        isVocabularyExpand = !it.isVocabularyExpand,
                        isGrammarExpanded = false
                    )
                }
            }
        }
    }
}