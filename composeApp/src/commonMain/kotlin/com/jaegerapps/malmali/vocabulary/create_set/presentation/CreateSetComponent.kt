package com.jaegerapps.malmali.vocabulary.create_set.presentation

import VocabularySetSourceFunctions
import com.arkivanov.decompose.ComponentContext
import com.jaegerapps.malmali.vocabulary.domain.UiFlashcard
import com.jaegerapps.malmali.vocabulary.domain.VocabSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class CreateSetComponent(
    componentContext: ComponentContext,
    val setTitle: String?,
    val date: Long?,
    val setId: Long?,
    private val vocabFunctions: VocabularySetSourceFunctions,
    private val onComplete: () -> Unit,
    private val onModalNavigate: (String) -> Unit,
) : ComponentContext by componentContext {

    private val _state = MutableStateFlow(CreateSetUiState())
    private val scope = CoroutineScope(Dispatchers.IO)

    val state = _state


    init {
        //If we are going to edit a set, we check it here. In order to get a set, we need the id, title, and date.
        //The date is used to check for a single set, so the names could be duplicates, but with the date, it returns one set.
        if (setId != null && setTitle != null && date != null) {
            println("setId")
            println(setId)
            val combineFlow = combine(
                vocabFunctions.getSetAsFlow(setTitle, date = date),
                vocabFunctions.getAllSetCards(setId)
            ) { set, cards ->
                //We use a uiId for the cards in the ui. This is given because the id of the card is used for the database, not for the ui
                val uiIdAddedCards = (cards.indices).map {
                    cards[it].copy(
                        uiId = it.toLong()
                    )
                }
                _state.update {
                    it.copy(
                        title = set.title,
                        uiFlashcards = uiIdAddedCards,
                        icon = set.icon,
                        isPrivate = set.isPrivate
                    )
                }
            }
            scope.launch {
                //takes while these values don't exist
                combineFlow.takeWhile { _state.value.title == "" && _state.value.uiFlashcards.isEmpty() }
                    .collect()
            }
        } else if (_state.value.uiFlashcards.isEmpty()){
            //creates 10 UiFlashcards for the user to enter.
           _state.update {
               it.copy(
                   uiFlashcards = (1..10).map {
                       UiFlashcard(
                           uiId = it.toLong(),
                           word = "",
                           def = "",
                           level = 1,
                           error = false
                       )
                   }
               )
           }
        }

    }

    fun onEvent(event: CreateSetUiEvent) {
        when (event) {
            CreateSetUiEvent.AddCard -> {
                //adds a card, gets the last uiId then we use this to increment from there
                val id = state.value.uiFlashcards.lastOrNull()?.uiId
                _state.update {
                    it.copy(
                        uiFlashcards = it.uiFlashcards.plus(
                            UiFlashcard(
                                uiId = if (id != null) id + 1 else 1,
                                word = "",
                                def = "",
                                level = 1,
                                error = false
                            )
                        )
                    )
                }
            }

            CreateSetUiEvent.AddCards -> {
                //used for on long press. No indicator how to do this tbh, might have to reconsider this.
                val newCards = addCards()
                _state.update {
                    it.copy(
                        uiFlashcards = it.uiFlashcards.plus(
                            newCards
                        )
                    )
                }
            }

            is CreateSetUiEvent.OnClearErrorVocab -> {
                //when an error appears, this will clear it.
                _state.update {
                    it.copy(
                        uiFlashcards = it.uiFlashcards.map { card ->
                            if (card.uiId == event.cardId) card.copy(
                                error = false
                            ) else card
                        }
                    )
                }
            }

            is CreateSetUiEvent.ChangePublicSetting -> {
                _state.update {
                    it.copy(
                        isPrivate = event.viewSetting
                    )
                }
            }

            is CreateSetUiEvent.TogglePopUp -> {
                //Here we are just going to toggle the popup menu.
                //I used the mode because I figured it would be easier to pass the functions for the popup.
                //With this, the popup has a single function that won't change, and the Ui text will adjust based on the mode.
                when (event.mode) {
                    PopUpMode.DELETE -> {
                        //changes mode to delete, so the popup messages will change
                        _state.update {
                            it.copy(
                                showSavePopUp = true,
                                mode = event.mode
                            )
                        }
                    }
                    PopUpMode.SAVE -> {
                        //changes mode to save, checks for errors, then changes popup to true and the mode to SAVE
                        val error = checkForBlanks()
                        if (error == null) {
                            _state.update {
                                it.copy(
                                    showSavePopUp = !it.showSavePopUp,
                                    mode = event.mode
                                )
                            }
                        } else {
                            _state.update {
                                it.copy(
                                    error = error
                                )
                            }
                        }
                    }
                    PopUpMode.DISMISS -> {
                        _state.update {
                            it.copy(
                                showSavePopUp = false,
                                mode = null
                            )
                        }
                    }
                }

            }

            CreateSetUiEvent.ConfirmPopUp -> {
                //Based on the mode, we get to this part.
                when (_state.value.mode) {
                    PopUpMode.DELETE -> {
                        scope.launch {
                            if (setId != null) {
                                //If we have a set that was being edited, all will be erased. then we go to home screen
                                vocabFunctions.deleteSet(setId)
                                vocabFunctions.deleteAllCards(setId)
                                onComplete()
                            } else {
                                //There is no set, we go to home screen
                                onComplete()
                            }
                        }
                    }

                    PopUpMode.SAVE -> {
                        //Made a vocab set to bring all the elements of the UI together. This is where we set the date stamp for this.
                        val vocabSet = VocabSet(
                            title = _state.value.title,
                            icon = _state.value.icon,
                            setId = setId,
                            expanded = false,
                            isPrivate = _state.value.isPrivate,
                            dateCreated = date ?: Clock.System.now().toEpochMilliseconds()
                        )
                        scope.launch {

                            if (setId != null && setTitle != null && date != null) {
                                //this means we are editing a set, so we just update it based on this
                                vocabFunctions.updateSet(
                                    set = vocabSet
                                )
                            } else {
                                //Adding a set
                                vocabFunctions.addSet(
                                    id = null,
                                    title = vocabSet.title,
                                    size = _state.value.uiFlashcards.size.toLong(),
                                    tags = null,
                                    isPrivate = vocabSet.isPrivate,
                                    dateCreated = vocabSet.dateCreated
                                )
                            }
                            //In order to link the flashcards with the set, we need a reference to the setId.
                            val setId = vocabFunctions.getSet(title = vocabSet.title, date = vocabSet.dateCreated).setId
                            if (setId != null) {
                                vocabFunctions.insertCards(_state.value.uiFlashcards, setId)
                            } else {
                                /*TODO - Error handling*/
                            }
                        }
                        //moved outside of scope b/c this has to run on the main, can't do it inside a coroutine
                        onComplete()

                    }
                    else -> {
                        //Shouldn't be able to get here
                    }
                }

            }

            is CreateSetUiEvent.DeleteWord -> {
                val toBeDeleted = state.value.uiFlashcards.firstOrNull { it.uiId == event.cardId }
                toBeDeleted?.let { card ->
                    _state.update {
                        it.copy(
                            uiFlashcards = it.uiFlashcards.minus(card)
                        )
                    }
                    if (card.cardId != null) {
                        //the card id is the reference to the id inside the database, if it is not null, then it is not a new card.
                        //Maybe I should make it so you don't delete something until you confirm that you want to save it...
                        //TODO - Make a to delete list as part of the ui, and then delete the list from the db on save. Boo
                        scope.launch {
                            vocabFunctions.deleteSingleCard(card)
                        }
                    }
                }
            }

            is CreateSetUiEvent.EditDef -> {
                val editDefCard = state.value.uiFlashcards.firstOrNull { it.uiId == event.cardId }
                editDefCard?.let { card ->
                    _state.update {
                        it.copy(
                            uiFlashcards = it.uiFlashcards.updateFlashcard(
                                id = card.uiId!!,
                                word = card.word,
                                def = event.text,
                                )
                        )
                    }
                }
            }

            is CreateSetUiEvent.EditTitle -> {
                _state.update {
                    it.copy(
                        title = event.text
                    )
                }
            }

            is CreateSetUiEvent.EditWord -> {
                val editDefCard = state.value.uiFlashcards.firstOrNull { it.uiId == event.cardId }
                editDefCard?.let { card ->
                    _state.update {
                        it.copy(
                            uiFlashcards = it.uiFlashcards.updateFlashcard(
                                id = card.uiId!!,
                                word = event.text,
                                def = card.def
                            )
                        )
                    }
                }
            }

            CreateSetUiEvent.OnClearUiError -> {
                _state.update {
                    it.copy(
                        error = null
                    )
                }
            }

            CreateSetUiEvent.OpenIconSelection -> TODO()
            is CreateSetUiEvent.SelectIcon -> TODO()
            is CreateSetUiEvent.OnModalNavigate -> {
                onModalNavigate(event.route)
            }
        }
    }

    private fun addCards(): List<UiFlashcard> {
        var list = emptyList<UiFlashcard>()
        val id = state.value.uiFlashcards.lastOrNull()?.uiId

        for (i in 1..10) {
            list = list.plus(UiFlashcard(if (id != null) id + i else 1, cardId = null, "", "", 0, error = false))
        }
        return list
    }

    private fun checkForBlanks(): UiError? {
        if (state.value.uiFlashcards.isEmpty()) return UiError.ADD_CARDS
        if (state.value.title.isBlank()) return UiError.MISSING_TITLE
        var hasError = false
        var index = 0
        val checkList = state.value.uiFlashcards.map {
            if (it.def.isBlank() || it.word.isBlank()) {
                it.copy(
                    error = true
                )
            } else it
        }
        _state.update {
            it.copy(
                uiFlashcards = checkList
            )
        }
        while (!hasError && index < state.value.uiFlashcards.size ) {
            hasError = state.value.uiFlashcards[index].error
            //size is 10, error 10th one, its index is 9
            //if index is 9, size is 10,
            index++
        }
        return if (hasError) {
            UiError.MISSING_WORD
        } else {
            null
        }
    }

    private fun List<UiFlashcard>.updateFlashcard(
        id: Long,
        word: String,
        def: String,
    ): List<UiFlashcard> =
        this.map { card -> if (card.uiId == id) card.copy(word = word, def = def) else card }

}

