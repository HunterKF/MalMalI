package com.jaegerapps.malmali.practice.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.jaegerapps.malmali.practice.domain.PracticeDataSource
import com.jaegerapps.malmali.practice.mappers.toUiHistoryItem
import com.jaegerapps.malmali.practice.models.UiHistoryItem
import core.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PracticeComponent(
    private val onNavigate: (String) -> Unit,
    private val practiceDataSource: PracticeDataSource,
    componentContext: ComponentContext,
) : ComponentContext by componentContext {

    private val _state = MutableStateFlow(PracticeUiState())
    val state = combine(
        _state,
        practiceDataSource.getHistorySql(),
    ) { state, history ->
        _state.update {
            it.copy(
                history = history.map { it.toUiHistoryItem() }
            )
        }
        state.copy(
            history = history.map { it.toUiHistoryItem() }
        )
    }.stateIn(
        CoroutineScope(Dispatchers.Main),
        SharingStarted.WhileSubscribed(5000),
        PracticeUiState()
    )

    init {
        lifecycle.doOnCreate {
            scope.launch {
                val sets = async { practiceDataSource.getSets() }.await()
                val grammar = async { practiceDataSource.getGrammar() }.await()

            }

        }
    }

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

            PracticeUiEvent.SavePractice -> {
                val newHistory = UiHistoryItem(
                    id = 0,
                    sentence = _state.value.text,
                    grammar = _state.value.currentGrammar!!,
                    vocab = _state.value.currentVocabulary!!,
                )
                scope.launch {
                    when (val result = practiceDataSource.insertHistorySql(newHistory)) {
                        is Resource.Error -> _state.update {
                            it.copy(
                                errorMessage = result.throwable?.message ?: "Unknown error"
                            )
                        }

                        is Resource.Success -> _state.update { it.copy(text = "") }
                    }
                }
            }

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

            PracticeUiEvent.CloseDropDowns -> TODO()
            PracticeUiEvent.RefreshPracticeContainer -> TODO()
        }
    }
}