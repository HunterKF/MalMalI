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
    private val increment = 10

    init {
        scope.launch {
            _state.update {
                it.copy(loading = true)
            }
            when (val result = async {
                repo.getAllRemotePublicSets(
                    _state.value.startPageRange,
                    _state.value.endPageRange
                )
            }.await()) {
                is Resource.Error -> {
                    _state.update {
                        it.copy(loading = false, error = result.throwable?.message)
                    }
                }

                is Resource.Success -> {
                    if (result.data != null) {
                        _state.update {
                            it.copy(
                                sets = it.sets.plus(result.data),
                                loading = false
                            )
                        }
                    } else {
                        _state.update {
                            it.copy(loading = false, error = result.throwable?.message)
                        }
                    }
                }
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
                if (_state.value.endOfRange) {
                    _state.update {
                        it.copy(
                            error = "End of range."
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            loadingMore = true,
                            startPageRange = it.startPageRange + increment,
                            endPageRange = it.endPageRange + increment
                        )
                    }
                    scope.launch {
                        when (val result = async {
                            repo.getAllRemotePublicSets(
                                _state.value.startPageRange,
                                _state.value.endPageRange
                            )
                        }.await()) {
                            is Resource.Error -> _state.update { it.copy(error = result.throwable?.message) }
                            is Resource.Success -> {
                                if (!result.data.isNullOrEmpty()) {
                                    _state.update {
                                        it.copy(
                                            sets = it.sets.plus(result.data),
                                            loadingMore = false,
                                        )
                                    }
                                } else {
                                    _state.update {
                                        it.copy(
                                            endOfRange = true,
                                            loadingMore = false,
                                        )
                                    }
                                }
                            }
                        }
                    }
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

            SearchUiEvent.SearchByTitle -> {
                if (_state.value.searchText.isBlank()) return
                _state.update {
                    it.copy(
                        startPageRange = 0L,
                        endPageRange = 9L,
                        sets = emptyList(),
                        loading = true,
                        endOfRange = false,
                        error = null
                    )
                }
                scope.launch {
                    when (val result = repo.searchPublicSets(
                        title = _state.value.searchText,
                        start = _state.value.startPageRange,
                        end = _state.value.endPageRange
                    )) {
                        is Resource.Error -> _state.update { it.copy(error = result.throwable?.message) }
                        is Resource.Success -> {
                            if (!result.data.isNullOrEmpty()) {
                                _state.update {
                                    it.copy(
                                        sets = it.sets.plus(result.data),
                                        loading = false,
                                    )
                                }
                            } else {
                                _state.update {
                                    it.copy(
                                        endOfRange = true,
                                        loading = false,
                                    )
                                }
                            }
                        }
                    }
                }
            }

            is SearchUiEvent.SelectSet -> {
                _state.update {
                    it.copy(
                        selectedSet = event.set,
                        showPopUp = true
                    )
                }
            }

            SearchUiEvent.TogglePopUp -> {

                _state.update {
                    it.copy(
                        showPopUp = !it.showPopUp,
                        selectedSet = if (it.showPopUp) null else it.selectedSet
                    )
                }
            }
        }
    }
}