package com.jaegerapps.malmali.vocabulary.study_flashcards

import VocabularySetSourceFunctions
import com.arkivanov.decompose.ComponentContext
import com.jaegerapps.malmali.vocabulary.domain.UiFlashcard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StudyFlashcardsComponent(
    componentContext: ComponentContext,
    private val database: VocabularySetSourceFunctions,
    private val onCompleteNavigate: () -> Unit,
    private val onNavigate: (String) -> Unit,
    private val onEditNavigate: (String, Long, Long) -> Unit,
    setId: Long,
    setTitle: String,
    date: Long,
) : ComponentContext by componentContext {

    private val _state = MutableStateFlow(StudyFlashcardsUiState())
    private val scope = CoroutineScope(Dispatchers.IO)

    val state = combine(
        _state,
        database.getAllSetCards(setId),
        database.getSetAsFlow(setTitle, date)
    ) { newState, cards, set ->
        val cardWithUiId = (cards.indices).map {
            cards[it].copy(
                uiId = it.toLong()
            )
        }
        val state = newState.copy(
            set = if (this._state.value.set == null) set else this._state.value.set,
            cards = this._state.value.cards.ifEmpty { cardWithUiId },
            currentCard = this._state.value.currentCard ?: cardWithUiId.first(),
            currentIndex = if (this._state.value.currentIndex == -1) 0 else this._state.value.currentIndex,
            showBack = this._state.value.showBack,
            isComplete = this._state.value.isComplete,
            experienceGained = this._state.value.experienceGained,
            uiError = this._state.value.uiError
        )
        _state.update {
            it.copy(
                set = set,
                cards = if (_state.value.cards.isEmpty()) {
                    cardWithUiId
                } else {
                    it.cards
                },
                currentCard = it.currentCard ?: cardWithUiId.first(),
                currentIndex = if (it.currentIndex == -1) 0 else it.currentIndex,
                showBack = it.showBack,
                isComplete = it.isComplete,
                experienceGained = it.experienceGained,
                uiError = it.uiError
            )
        }
        state
    }.stateIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(300L),
        initialValue = StudyFlashcardsUiState()
    )


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
                            currentCard = _state.value.cards[newIndex]
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
                val cardToUpdate = _state.value.currentCard!!.copy(
                    level = _state.value.currentCard!!.level - 1
                )
                val newList = moveCardToEnd(
                    _state.value.currentIndex,
                    card = cardToUpdate,
                    cards = _state.value.cards
                )
                scope.launch {
                    _state.update {
                        it.copy(
                            cards = newList,
                            currentCard = newList[it.currentIndex]
                        )
                    }
                    println("dead here")
                    database.updateCard(cardToUpdate)
                }
            }

            is StudyFlashcardsUiEvent.OnGotItClick -> {
                scope.launch {
                    val cardToUpdate = state.value.currentCard!!.copy(
                        level = 2
                    )
                    database.updateCard(cardToUpdate)

                    //size = 5, current index is 4
                    val newIndex = state.value.currentIndex + 1

                    if (newIndex <= state.value.cards.size - 1) {
                        _state.update {
                            it.copy(
                                currentCard = it.cards[newIndex],
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

                    if (newIndex <= currentState.cards.size - 1) {
                        currentState.copy(
                            currentCard = currentState.cards[newIndex],
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
                val repeatList = _state.value.cards.sortedBy { it.level }
                println("repeatList")
                println(repeatList)
                _state.update {
                    it.copy(
                        cards = repeatList,
                        currentIndex = 0,
                        currentCard = repeatList[0],
                        isComplete = false
                    )
                }
            }

            StudyFlashcardsUiEvent.OnSetEditClick -> {
                _state.value.set?.let { set ->
                    set.setId?.let { onEditNavigate(set.title, it, set.dateCreated) }

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
    card: UiFlashcard,
    cards: List<UiFlashcard>,
): List<UiFlashcard> {
    println("List before being moved")
    println(cards)
    val dropList: MutableList<UiFlashcard> = cards.toMutableList()
    dropList.removeAt(currentIndex)
    dropList.add(card)
    println("List after being moved")
    println(dropList)
    return dropList.toList()
}