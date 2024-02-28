package com.jaegerapps.malmali.grammar

import com.arkivanov.decompose.ComponentContext
import com.jaegerapps.malmali.grammar.models.GrammarLevel
import com.jaegerapps.malmali.grammar.presentation.GrammarUiEvent
import com.jaegerapps.malmali.grammar.presentation.GrammarUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class GrammarScreenComponent(
    componentContext: ComponentContext,
    private val onNavigate: (String) -> Unit,
    grammar: List<GrammarLevel>
) : ComponentContext by componentContext {

    private val _state = MutableStateFlow(GrammarUiState())
    val state = _state

    val scope = CoroutineScope(Dispatchers.IO)

    init {
        _state.update {
            it.copy(
                levels = grammar
            )
        }
    }
    fun onEvent(event: GrammarUiEvent) {
        when (event) {
            is GrammarUiEvent.DeselectAllFromLevel -> TODO()
            is GrammarUiEvent.DeselectGrammarPoint -> TODO()
            is GrammarUiEvent.OnNavigate -> {
                onNavigate(event.route)
            }
            is GrammarUiEvent.SelectAllFromLevel -> TODO()
            is GrammarUiEvent.SelectGrammarPoint -> TODO()
            is GrammarUiEvent.ToggleLevelExpansion -> TODO()
            is GrammarUiEvent.TogglePointExpansion -> TODO()
            GrammarUiEvent.ToggleEditMode -> {
                _state.update {
                    it.copy(
                        isEditing = !it.isEditing
                    )
                }
            }
        }
    }

}