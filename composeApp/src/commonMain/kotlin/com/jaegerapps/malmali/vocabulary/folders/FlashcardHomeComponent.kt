package com.jaegerapps.malmali.vocabulary.folders

import VocabularySetSourceFunctions
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.jaegerapps.malmali.vocabulary.folders.presentation.FolderUiEvent
import com.jaegerapps.malmali.vocabulary.folders.presentation.VocabHomeUiState
import com.jaegerapps.malmali.vocabulary.models.VocabSet
import core.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlashcardHomeComponent(
    componentContext: ComponentContext,
    database: VocabularySetSourceFunctions,
    private val onNavigateBack: () -> Unit,
    private val onNavigateToCreateScreen: () -> Unit,
    private val onNavigateToStudyCard: (Int, String, String) -> Unit,
    private val onNavigateToEdit: (String, Int, String) -> Unit,
    private val onModalNavigate: (String) -> Unit,
    sets: List<VocabSet>,
) : ComponentContext by componentContext {
    private val _state = MutableStateFlow(VocabHomeUiState())
    val scope = CoroutineScope(Dispatchers.IO)

    val state = _state


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