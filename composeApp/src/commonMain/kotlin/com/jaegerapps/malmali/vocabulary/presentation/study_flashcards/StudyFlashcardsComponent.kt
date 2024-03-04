package com.jaegerapps.malmali.vocabulary.presentation.study_flashcards

import com.jaegerapps.malmali.vocabulary.domain.repo.VocabularyRepo
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel
import com.jaegerapps.malmali.vocabulary.domain.models.VocabularyCardModel
import core.Knower
import core.Knower.e
import core.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StudyFlashcardsComponent(
    componentContext: ComponentContext,
    private val database: VocabularyRepo,
    private val onCompleteNavigate: () -> Unit,
    private val onNavigate: (String) -> Unit,
    private val onEditNavigate: (Int, Int) -> Unit,
    private val remoteId: Int,
    private val setId: Int,
) : ComponentContext by componentContext {

    private val _state = MutableStateFlow(StudyFlashcardsUiState())
    private val scope = CoroutineScope(Dispatchers.IO)

    val state = _state

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


    fun onEvent(event: StudyFlashcardsUiEvent) {
        when (event) {
            StudyFlashcardsUiEvent.OnFlipCard -> {
                _state.update {
                    it.copy(
                        showBack = !it.showBack
                    )
                }
            }

            StudyFlashcardsUiEvent.OnPrevious -> {
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


            StudyFlashcardsUiEvent.OnCardFlipClick -> {
                _state.update {
                    it.copy(
                        showBack = !it.showBack
                    )
                }
            }

            is StudyFlashcardsUiEvent.OnDontKnowClick -> {

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

            is StudyFlashcardsUiEvent.OnGotItClick -> {
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

            is StudyFlashcardsUiEvent.OnModalClick -> {
                event.openModal()
            }

            is StudyFlashcardsUiEvent.OnModalNavigate -> {
                /*TODO - navigate*/
                onNavigate(event.route)

            }

            StudyFlashcardsUiEvent.OnForward -> {
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

            StudyFlashcardsUiEvent.OnRepeatClick -> {
                _state.update {
                    it.copy(
                        currentIndex = 0,
                        currentCard = it.set!!.cards[0],
                        isComplete = false
                    )
                }
            }

            StudyFlashcardsUiEvent.OnSetEditClick -> {
                _state.value.set?.let { set ->
                    set.localId?.let { onEditNavigate( it,set.remoteId!!) }

                }
            }

            StudyFlashcardsUiEvent.OnSetShareClick -> {
                /*TODO - Navigate to share*/
            }

            StudyFlashcardsUiEvent.OnSettingsClick -> {
                /*TODO - Navigate to settings*/
            }

            is StudyFlashcardsUiEvent.OnFolderClick -> {
                event.onClick()
            }

            StudyFlashcardsUiEvent.OnCompleteClick -> {
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