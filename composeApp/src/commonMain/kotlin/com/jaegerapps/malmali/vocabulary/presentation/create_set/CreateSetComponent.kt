package com.jaegerapps.malmali.vocabulary.presentation.create_set

import com.jaegerapps.malmali.vocabulary.domain.repo.VocabularyRepo
import com.arkivanov.decompose.ComponentContext
import com.jaegerapps.malmali.components.models.IconResource
import com.jaegerapps.malmali.login.domain.UserData
import com.jaegerapps.malmali.vocabulary.domain.models.VocabularyCardModel
import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel
import core.Knower
import core.Knower.d
import core.Knower.e
import core.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateSetComponent(
    componentContext: ComponentContext,
    val remoteId: Int?,
    val localId: Int?,
    val isAuthor: Boolean?,
    private val repo: VocabularyRepo,
    private val userData: UserData,
    private val onComplete: () -> Unit,
    private val onModalNavigate: (String) -> Unit,
) : ComponentContext by componentContext {

    private val _state = MutableStateFlow(CreateSetUiState())
    private val scope = CoroutineScope(Dispatchers.IO)

    val state = _state


    init {
        //If we are going to edit a set, we check it here. In order to get a set, we need the id, title, and date.
        //The date is used to check for a single set, so the names could be duplicates, but with the date, it returns one set.
        if (localId != null && remoteId != null) {
            scope.launch {
                _state.update { it.copy(isLoading = true) }
                when (val result = async { repo.getLocalSet(localId, remoteId) }.await()) {
                    is Resource.Error -> _state.update { it.copy(error = UiError.UNKNOWN_ERROR) }
                    is Resource.Success -> {
                        if (result.data != null) {
                            val set = result.data
                            Knower.d("createSet init", "This is the result ${result.data}")
                            _state.update {
                                it.copy(
                                    title = set.title,
                                    vocabularyCardModels = addUiIds(set.cards),
                                    icon = set.icon,
                                    isPublic = set.isPublic,
                                    isLoading = false,
                                    dateCreated = set.dateCreated
                                )
                            }
                            Knower.d("createSet init", "This is the state ${_state.value}")

                        }
                    }
                }
            }
        } else if (_state.value.vocabularyCardModels.isEmpty()) {
            //creates 10 UiFlashcards for the user to enter.
            _state.update {
                it.copy(
                    vocabularyCardModels = (1..10).map { id ->
                        VocabularyCardModel(
                            uiId = id,
                            word = "",
                            definition = "",
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
                val id = state.value.vocabularyCardModels.lastOrNull()?.uiId
                _state.update {
                    it.copy(
                        vocabularyCardModels = it.vocabularyCardModels.plus(
                            VocabularyCardModel(
                                uiId = if (id != null) id + 1 else 1,
                                word = "",
                                definition = "",
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
                        vocabularyCardModels = it.vocabularyCardModels.plus(
                            newCards
                        )
                    )
                }
            }

            is CreateSetUiEvent.OnClearErrorVocab -> {
                //when an error appears, this will clear it.
                _state.update {
                    it.copy(
                        vocabularyCardModels = it.vocabularyCardModels.map { card ->
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
                        isPublic = event.viewSetting
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
                            if (localId != null && remoteId != null && isAuthor != null) {
                                //If we have a set that was being edited, all will be erased. then we go to home screen
                                Knower.e("CreateSetComponent", "Here is the localId $localId and remoteId $remoteId")
                                async { repo.deleteSet(localId, remoteId, isAuthor) }.await()
                                withContext(Dispatchers.Main) { onComplete() }
                            } else {
                                //There is no set, we go to home screen
                                withContext(Dispatchers.Main) { onComplete() }

                            }
                        }
                    }

                    PopUpMode.SAVE -> {
                        //Made a vocab set to bring all the elements of the UI together.


                        scope.launch {

                            if (localId != null && remoteId != null && isAuthor != null) {
                                //this means we are editing a set, so we just update it based on this
                                val vocabSetModel = VocabSetModel(
                                    title = _state.value.title,
                                    icon = _state.value.icon ?: IconResource.resourceFromTag("bear 1"),
                                    localId = localId,
                                    isPublic = _state.value.isPublic,
                                    tags = _state.value.tags,
                                    dateCreated = _state.value.dateCreated,
                                    cards = _state.value.vocabularyCardModels,
                                    isAuthor = isAuthor,
                                    remoteId = remoteId
                                )

                                repo.updateSet(
                                    set = vocabSetModel
                                )
                            } else {
                                //Adding a set
                                val vocabSetModel = VocabSetModel(
                                    title = _state.value.title,
                                    icon = _state.value.icon ?: IconResource.resourceFromTag("bear 1"),
                                    localId = 0,
                                    isPublic = _state.value.isPublic,
                                    tags = _state.value.tags,
                                    dateCreated = _state.value.dateCreated,
                                    cards = _state.value.vocabularyCardModels,
                                    isAuthor = true,
                                    remoteId = 0
                                )
                                repo.createSet(
                                    vocabSetModel
                                )
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
                val toBeDeleted = state.value.vocabularyCardModels[event.cardIndex]
                _state.update {
                    it.copy(
                        vocabularyCardModels = it.vocabularyCardModels.minus(toBeDeleted)
                    )
                }
                /*This will just remove a card from the Ui card list, it does not delete anything.*/
            }

            is CreateSetUiEvent.EditDef -> {
                val editDefCard = state.value.vocabularyCardModels.firstOrNull { it.uiId == event.cardId }
                editDefCard?.let { card ->
                    _state.update {
                        it.copy(
                            vocabularyCardModels = it.vocabularyCardModels.updateFlashcard(
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
                val editDefCard = state.value.vocabularyCardModels.firstOrNull { it.uiId == event.cardId }
                editDefCard?.let { card ->
                    _state.update {
                        it.copy(
                            vocabularyCardModels = it.vocabularyCardModels.updateFlashcard(
                                id = card.uiId!!,
                                word = event.text,
                                def = card.definition
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

            CreateSetUiEvent.OpenIconSelection -> {

            }
            is CreateSetUiEvent.SelectIcon -> {

            }
            is CreateSetUiEvent.OnModalNavigate -> {
                onModalNavigate(event.route)
            }
        }
    }

    private fun addCards(): List<VocabularyCardModel> {
        var list = emptyList<VocabularyCardModel>()
        val id = state.value.vocabularyCardModels.lastOrNull()?.uiId

        for (i in 1..10) {
            list = list.plus(
                VocabularyCardModel(
                    uiId = if (id != null) id + i else 1,
                    word = "",
                    definition = "",
                    error = false
                )
            )
        }
        return list
    }

    private fun checkForBlanks(): UiError? {
        if (state.value.vocabularyCardModels.isEmpty()) return UiError.ADD_CARDS
        if (state.value.title.isBlank()) return UiError.MISSING_TITLE
        var hasError = false
        var index = 0
        val checkList = state.value.vocabularyCardModels.map {
            if (it.definition.isBlank() || it.word.isBlank()) {
                it.copy(
                    error = true
                )
            } else it
        }
        _state.update {
            it.copy(
                vocabularyCardModels = checkList
            )
        }
        while (!hasError && index < state.value.vocabularyCardModels.size) {
            hasError = state.value.vocabularyCardModels[index].error
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

    private fun List<VocabularyCardModel>.updateFlashcard(
        id: Int,
        word: String,
        def: String,
    ): List<VocabularyCardModel> =
        this.map { card -> if (card.uiId == id) card.copy(word = word, definition = def) else card }

    private fun addUiIds(list: List<VocabularyCardModel>): List<VocabularyCardModel> {
        var updatedList = emptyList<VocabularyCardModel>()
        var index = 0
        while (index < list.size) {
            updatedList = updatedList.plus(list[index].copy(uiId = index))
            index ++
        }
        return updatedList
    }

}

