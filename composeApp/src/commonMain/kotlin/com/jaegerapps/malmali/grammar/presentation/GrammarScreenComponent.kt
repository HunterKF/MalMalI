package com.jaegerapps.malmali.grammar.presentation

import com.arkivanov.decompose.ComponentContext
import com.jaegerapps.malmali.grammar.domain.repo.GrammarRepo
import com.jaegerapps.malmali.grammar.domain.models.GrammarLevelModel
import core.Knower
import core.Knower.d
import core.Knower.e
import core.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GrammarScreenComponent(
    componentContext: ComponentContext,
    private val repo: GrammarRepo,
    private val onNavigate: (String) -> Unit,
    private val isPro: Boolean,
    private val grammarLevelModels: List<GrammarLevelModel>,
) : ComponentContext by componentContext {

    private val _state = MutableStateFlow(GrammarUiState())
    val state = _state

    val scope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            readSelectedLevels()
        }
    }

    fun onEvent(event: GrammarUiEvent) {
        when (event) {
            is GrammarUiEvent.OnNavigate -> {
                onNavigate(event.route)
            }

            is GrammarUiEvent.ToggleLevelSelection -> {
                val list =
                    if (_state.value.selectedLevels.contains(event.level.id)) _state.value.selectedLevels.minus(
                        event.level.id
                    ) else _state.value.selectedLevels.plus(event.level.id)
                if (list.isEmpty()) {
                    _state.update {
                        it.copy(
                            error = "Can't be empty. Please select something."
                        )
                    }
                } else {
                    Knower.d("ToggleLevelSelection", "Updated the list: $list")
                    scope.launch {
                        updateLevel(list)
                    }
                }
            }

            GrammarUiEvent.ToggleEditMode -> {
                _state.update {
                    it.copy(
                        isEditing = !it.isEditing
                    )
                }
            }

            GrammarUiEvent.ClearError -> {
                _state.update {
                    it.copy(error = null)
                }
            }
        }
    }

    private suspend fun readSelectedLevels() {
        when (val result = repo.readSelectedLevels()) {
            is Resource.Error -> {
                Knower.e(
                    "GrammarScreenComponent",
                    "An error occurred. ${result.throwable?.message}"
                )
            }

            is Resource.Success -> {
                if (!result.data.isNullOrEmpty()) {
                    _state.update {
                        it.copy(
                            selectedLevels = result.data,
                            grammarLevelModelList = grammarLevelModels.map { level ->
                                level.copy(
                                    isSelected = result.data.contains(level.id)
                                )
                            }
                        )
                    }
                } else {
                    Knower.e(
                        "GrammarScreenComponent",
                        "An error occurred. ${result.throwable?.message}"
                    )
                }
            }
        }
    }

    private suspend fun updateLevel(list: List<Int>) {
        when (val result = repo.updateSelectedLevels(list)) {
            is Resource.Error -> {
                Knower.e(
                    "GrammarScreenComponent",
                    "An error occurred. ${result.throwable?.message}"
                )
            }

            is Resource.Success -> {
                if (!result.data.isNullOrEmpty()) {
                    _state.update {
                        it.copy(
                            selectedLevels = result.data,
                            grammarLevelModelList = grammarLevelModels.map { level ->
                                level.copy(
                                    isSelected = result.data.contains(level.id)
                                )
                            }
                        )
                    }
                } else {
                    Knower.e(
                        "GrammarScreenComponent",
                        "An error occurred. ${result.throwable?.message}"
                    )
                }
            }
        }
    }

}