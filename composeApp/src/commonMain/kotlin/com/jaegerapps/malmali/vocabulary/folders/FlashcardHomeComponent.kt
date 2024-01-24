package com.jaegerapps.malmali.vocabulary.folders

import VocabularySetSourceFunctions
import com.arkivanov.decompose.ComponentContext
import com.jaegerapps.malmali.vocabulary.folders.presentation.FolderUiEvent
import com.jaegerapps.malmali.vocabulary.folders.presentation.VocabHomeUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class FlashcardHomeComponent(
    componentContext: ComponentContext,
    private val database: VocabularySetSourceFunctions,
    private val onNavigateBack: () -> Unit,
    private val onNavigateToCreateScreen: () -> Unit,
    private val onNavigateToStudyCard: (Long, String, Long) -> Unit,
    private val onNavigateToEdit: (String, Long, Long) -> Unit,
    private val onModalNavigate: (String) -> Unit,
) : ComponentContext by componentContext {
    private val _state = MutableStateFlow(VocabHomeUiState())
    val scope = CoroutineScope(Dispatchers.IO)

    val state = combine(
        _state,
        database.getAllSets()
    ) { state, list ->
        state.copy(
            setList = list
        )
    }.stateIn(scope, SharingStarted.WhileSubscribed(5000L), VocabHomeUiState())



    fun onEvent(event: FolderUiEvent) {
        when (event) {
            is FolderUiEvent.OnEditClick -> {
                println("Route stuff")
                println(event.setId)
                println(event.setTitle)
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

            FolderUiEvent.GetAllCards -> {
//                scope.launch {
//                    val folders = database.getAllSets()
//                }
            }

            is FolderUiEvent.OnModalNavigate -> {
                onModalNavigate(event.route)
            }
        }
    }
}