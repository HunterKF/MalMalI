package com.jaegerapps.malmali.vocabulary.presentation.folders

import com.jaegerapps.malmali.vocabulary.domain.repo.VocabularyRepo
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.jaegerapps.malmali.vocabulary.presentation.folders.presentation.FolderUiEvent
import com.jaegerapps.malmali.vocabulary.presentation.folders.presentation.VocabHomeUiState
import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlashcardHomeComponent(
    componentContext: ComponentContext,
    private val repo: VocabularyRepo,
    private val onNavigateBack: () -> Unit,
    private val onNavigateToCreateScreen: () -> Unit,
    private val onNavigateToStudyCard: (Int, String, String) -> Unit,
    private val onNavigateToEdit: (String, Int, String) -> Unit,
    private val onModalNavigate: (String) -> Unit,
    sets: List<VocabSetModel>,
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

    fun onEvent(event: FolderUiEvent) {
        when (event) {
            is FolderUiEvent.OnEditClick -> {
                onNavigateToEdit(event.setTitle, event.setId, event.date)
            }

            is FolderUiEvent.OnNavigateToCreateClick -> {
                onNavigateToCreateScreen()
            }

            is FolderUiEvent.OnShareClick -> {

            }

            is FolderUiEvent.OnStudyClick -> {
                onNavigateToStudyCard(event.setId, event.title, event.date)
            }

            is FolderUiEvent.OnModalNavigate -> {
                onModalNavigate(event.route)
            }
        }
    }
}