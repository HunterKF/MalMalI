package com.jaegerapps.malmali.vocabulary.presentation.search

import com.arkivanov.decompose.ComponentContext
import com.jaegerapps.malmali.vocabulary.domain.repo.VocabularyRepo
import core.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchSetComponent(
    componentContext: ComponentContext,
    private val repo: VocabularyRepo,
) : ComponentContext by componentContext {

    private val _state = MutableStateFlow(SearchUiState())
    val state = _state.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            _state.update {
                it.copy(loading = true)
            }
            when (val result = async { repo.getAllRemotePublicSets() }.await()) {
                is Resource.Error -> {
                    _state.update {
                        it.copy(loading = true)
                    }
                }
                is Resource.Success -> TODO()
            }

        }
    }

    fun onEvent(event: SearchUiEvent) {
        when (event) {
            SearchUiEvent.ClearUiMessage -> {
                _state.update {
                    it.copy(
                        error = null
                    )
                }
            }

            SearchUiEvent.LoadMore -> {
                /*TODO*/
                _state.update {
                    it.copy(
                        error = null
                    )
                }
            }

            is SearchUiEvent.OnSearchTextValueChange -> {
                _state.update { it.copy(searchText = event.value) }
            }

            SearchUiEvent.SaveSet -> {

                scope.launch {
                    withContext(Dispatchers.Main) {
                        _state.update {
                            it.copy(
                                loading = true
                            )
                        }
                    }
                    val result = _state.value.selectedSet?.let { repo.insertSetLocally(it) }
                    if (result != null && result.data == true) {
                        withContext(Dispatchers.Main) {
                            _state.update {
                                it.copy(
                                    selectedSet = null,
                                    searchText = "",
                                    error = "Successfully saved.",
                                    loading = false
                                )
                            }
                        }
                    } else {
                        _state.update {
                            it.copy(
                                selectedSet = null,
                                searchText = "",
                                error = "An error occurred.",
                                loading = false
                            )
                        }
                    }

                }
            }

            SearchUiEvent.SearchByName -> {
                repo
            }

            is SearchUiEvent.SelectSet -> TODO()
        }
    }
}