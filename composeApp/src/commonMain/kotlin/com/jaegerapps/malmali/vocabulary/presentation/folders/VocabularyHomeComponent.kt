package com.jaegerapps.malmali.vocabulary.presentation.folders

import com.jaegerapps.malmali.vocabulary.domain.repo.VocabularyRepo
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel
import core.Knower
import core.Knower.e
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VocabularyHomeComponent(
    componentContext: ComponentContext,
    repo: VocabularyRepo,
    private val onNavigateToCreateScreen: () -> Unit,
    private val onNavigateToStudyCard: (Int, Int) -> Unit,
    private val onNavigateToEdit: (Int, Int, Boolean) -> Unit,
    private val onModalNavigate: (String) -> Unit,
    sets: List<VocabSetModel>,
    private val onNavigateSearch: () -> Unit,
) : ComponentContext by componentContext {
    private val _state = MutableStateFlow(VocabHomeUiState())
    val scope = CoroutineScope(Dispatchers.IO)

    val state = combine(
        _state,
        repo.getAllLocalSets()
    ) { state, sets ->
        _state.update {
            it.copy(
                setList = sets
            )
        }
        state.copy(
            setList = sets
        )


    }.stateIn(scope, SharingStarted.WhileSubscribed(5000), VocabHomeUiState())


    init {
        lifecycle.doOnCreate {
            scope.launch {
                _state.update {
                    it.copy(
                        loading = false,
                        setList = sets
                    )
                }
            }
        }
    }

    fun onEvent(event: VocabHomeUiEvent) {
        when (event) {
            is VocabHomeUiEvent.OnEditClick -> {
                onNavigateToEdit(event.localId, event.remoteId, event.isAuthor)
            }

            is VocabHomeUiEvent.OnNavigateToCreateClick -> {
                onNavigateToCreateScreen()
            }

            is VocabHomeUiEvent.OnShareClick -> {

            }

            is VocabHomeUiEvent.OnStudyClick -> {
                Knower.e(
                    "OnStudyClick",
                    "id: ${event.localId}.remote id: ${event.remoteId}"
                )
                onNavigateToStudyCard(event.localId, event.remoteId)
            }

            is VocabHomeUiEvent.OnModalNavigate -> {
                onModalNavigate(event.route)
            }
            is VocabHomeUiEvent.OnNavigateToSearchClick -> {
                onNavigateSearch()
            }
        }
    }
}