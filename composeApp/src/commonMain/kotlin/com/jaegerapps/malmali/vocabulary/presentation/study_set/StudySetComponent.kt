package com.jaegerapps.malmali.vocabulary.presentation.study_set

import com.jaegerapps.malmali.vocabulary.domain.repo.VocabularyRepo
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.jaegerapps.malmali.common.models.VocabularyCardModel
import core.Knower
import core.Knower.e
import core.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StudySetComponent(
    componentContext: ComponentContext,
    private val database: VocabularyRepo,
    private val onCompleteNavigate: () -> Unit,
    private val onNavigate: (String) -> Unit,
    private val onEditNavigate: (Int, Int, Boolean) -> Unit,
    private val remoteId: Int,
    private val setId: Int,
) : ComponentContext by componentContext {

    private val _state = MutableStateFlow(StudySetUiState())
    private val scope = CoroutineScope(Dispatchers.IO)

    val state = _state.asStateFlow()

    init {
        lifecycle.doOnCreate {
            scope.launch {
                when (val result = database.getLocalSet(setId, remoteId)) {
                    is Resource.Error -> {
                        Knower.e(
                            "StudyFlashCards init",
                            "An error occurred: ${result.throwable?.message}"
                        )
                    }

                    is Resource.Success -> {
                        if (result.data != null) {
                            _state.update {
                                it.copy(
                                    loading = false,
                                    set = result.data,
                                    currentCard = result.data.cards.first(),
                                    currentIndex = 0
                                )
                            }
                        }
                    }
                }
            }
        }
    }


    fun onEvent(event: StudySetUiEvent) {
        when (event) {
            StudySetUiEvent.OnFlipCard -> {
                _state.update {
                    it.copy(
                        showBack = !it.showBack
                    )
                }
            }

            StudySetUiEvent.OnPrevious -> {
                if (_state.value.currentIndex >= 1) {
                    println(_state.value)
                    val newIndex = _state.value.currentIndex - 1
                    _state.update {
                        it.copy(
                            currentIndex = newIndex,
                            currentCard = _state.value.set?.cards?.get(newIndex)
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            uiError = StudyError.NO_BACK
                        )
                    }
                }
            }


            StudySetUiEvent.OnCardFlipClick -> {
                _state.update {
                    it.copy(
                        showBack = !it.showBack
                    )
                }
            }

            is StudySetUiEvent.OnDontKnowClick -> {

                val newList = moveCardToEnd(
                    _state.value.currentIndex,
                    card = _state.value.currentCard!!,
                    cards = _state.value.set!!.cards
                )
                scope.launch {
                    _state.update {
                        it.copy(
                            set = it.set!!.copy(
                                cards = newList
                            ),
                            currentCard = newList[it.currentIndex]
                        )
                    }
                }
            }

            is StudySetUiEvent.OnGotItClick -> {
                scope.launch {

                    //size = 5, current index is 4
                    val newIndex = state.value.currentIndex + 1

                    if (newIndex <= state.value.set!!.cards.size - 1) {
                        _state.update {
                            it.copy(
                                currentCard = it.set!!.cards[newIndex],
                                currentIndex = newIndex
                            )
                        }
                    } else {
                        _state.update {
                            it.copy(
                                isComplete = true
                            )
                        }
                    }

                }

            }

            is StudySetUiEvent.OnModalClick -> {
                event.openModal()
            }

            is StudySetUiEvent.OnModalNavigate -> {
                /*TODO - navigate*/
                onNavigate(event.route)

            }

            StudySetUiEvent.OnForward -> {
                _state.update { currentState ->
                    val newIndex = currentState.currentIndex + 1

                    if (newIndex <= currentState.set!!.cards.size - 1) {
                        currentState.copy(
                            currentCard = currentState.set.cards[newIndex],
                            currentIndex = newIndex
                        )
                    } else {
                        currentState.copy(
                            isComplete = true
                        )
                    }
                }

            }

            StudySetUiEvent.OnRepeatClick -> {
                _state.update {
                    it.copy(
                        currentIndex = 0,
                        currentCard = it.set!!.cards[0],
                        isComplete = false
                    )
                }
            }

            StudySetUiEvent.OnSetEditClick -> {
                _state.value.set?.let { set ->
                    set.localId?.let { onEditNavigate( it,set.remoteId!!, set.isAuthor) }

                }
            }

            StudySetUiEvent.OnSettingsClick -> {
                /*TODO - Navigate to settings*/
            }

            is StudySetUiEvent.OnFolderClick -> {
                event.onClick()
            }

            StudySetUiEvent.OnCompleteClick -> {
                onCompleteNavigate()
            }

        }
    }
}

private fun moveCardToEnd(
    currentIndex: Int,
    card: VocabularyCardModel,
    cards: List<VocabularyCardModel>,
): List<VocabularyCardModel> {
    val dropList: MutableList<VocabularyCardModel> = cards.toMutableList()
    dropList.removeAt(currentIndex)
    dropList.add(card)
    println(dropList)
    return dropList.toList()
}