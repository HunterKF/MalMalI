package com.jaegerapps.malmali.practice.presentation

import com.arkivanov.decompose.ComponentContext
import com.jaegerapps.malmali.common.models.GrammarLevelModel
import com.jaegerapps.malmali.common.models.VocabularySetModel
import com.jaegerapps.malmali.login.domain.UserData
import com.jaegerapps.malmali.practice.domain.repo.PracticeRepo
import com.jaegerapps.malmali.practice.domain.mappers.toHistoryModel
import com.jaegerapps.malmali.practice.domain.mappers.createHistoryModel
import core.Knower
import core.Knower.d
import core.Knower.e
import core.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class PracticeComponent(
    private val levelModelList: List<GrammarLevelModel>,
    private val userData: UserData,
    private val onNavigate: (String) -> Unit,
    private val repo: PracticeRepo,
    componentContext: ComponentContext,
) : ComponentContext by componentContext {

    private val _state = MutableStateFlow(PracticeUiState())
    val state = combine(
        _state,
        repo.getHistorySql(),
    ) { state, history ->
        val newHistory = history.map { it.toHistoryModel() }
        _state.update {
            it.copy(
                history = newHistory
            )
        }
        state.copy(
            history = newHistory
        )
    }.stateIn(
        CoroutineScope(Dispatchers.Default),
        SharingStarted.Lazily,
        PracticeUiState()
    )

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        _state.update {
            it.copy(isLoading = true)
        }
        scope.launch {
            //This returns only the saved selected levels from the user's settings
            val levels = readAndReturnSettingLevels()
            val selectedLevels = levelModelList.filter { levels.contains(it.id) }
                .map { it.copy(isSelected = true) }
            //Next we read the local sets. If null, that means nothing was there, so we have to get the supabase.
            val sets = readSets()
            if (sets != null) {
                //Sets exist, now we get the settings id, filter our sets by them, and return.
                //If nothing is selected, it returns an empty list, which means, go get supabase defaults
                val selectedSets = selectAndReturnSets(sets)
                if (selectedSets.isNotEmpty()) {
                    _state.update {
                        it.copy(
                            setModelList = sets,
                            selectedSet = selectedSets,
                            selectedLevel = selectedLevels,
                            currentGrammar = selectedLevels.first().grammarList.first(),
                            currentVocabulary = selectedSets.first().cards.first()
                        )
                    }
                }
            } else {
                val defaultSet = async { getDefaultSetFromRemote() }.await()
                if (defaultSet != null) {
                    _state.update {
                        it.copy(
                            setModelList = listOf(defaultSet),
                            selectedSet = listOf(defaultSet),
                            selectedLevel = selectedLevels,
                            currentGrammar = selectedLevels.first().grammarList.first(),
                            currentVocabulary = defaultSet.cards.first()
                        )
                    }
                }
            }


        }
    }


    fun onEvent(event: PracticeUiEvent) {
        when (event) {
            is PracticeUiEvent.OnNavigate -> onNavigate(event.route)
            is PracticeUiEvent.OnValueChanged -> {
                _state.update {
                    it.copy(
                        text = event.value
                    )
                }
            }

            PracticeUiEvent.SavePractice -> {
                if (_state.value.text.isNotBlank()) {
                    val newHistory = createHistoryModel(
                        _state.value.text,
                        _state.value.currentGrammar!!,
                        _state.value.currentVocabulary!!
                    )
                    scope.launch {
                        when (val result = repo.insertHistorySql(newHistory)) {
                            is Resource.Error -> {
                                Knower.e("SavePractice", "An error occurred: ${result.throwable}")
                                _state.update {
                                    it.copy(
                                        errorMessage = result.throwable?.message ?: "Unknown error"
                                    )
                                }
                            }

                            is Resource.Success -> {
                                Knower.e("SavePractice", "Saved")

                                _state.update { it.copy(text = "") }
                                changeCurrentValues()

                            }
                        }
                    }
                }
            }

            PracticeUiEvent.ToggleGrammarDropDown -> {
                _state.update {
                    it.copy(
                        isGrammarExpanded = !it.isGrammarExpanded,
                        isVocabularyExpand = false
                    )
                }
            }

            PracticeUiEvent.ToggleVocabDropDown -> {
                _state.update {
                    it.copy(
                        isVocabularyExpand = !it.isVocabularyExpand,
                        isGrammarExpanded = false
                    )
                }
            }

            PracticeUiEvent.CloseDropDowns -> TODO()
            PracticeUiEvent.RefreshPracticeContainer -> {
                changeCurrentValues()
            }
        }
    }

    private suspend fun readAndReturnSettingLevels(): List<Int> {
        //This will read the saved setting data for the selected levels.
        //If nothing has been selected, it returns -1, so we supply a default of 1 to it
        return when (val result = repo.readSelectedLevels()) {
            is Resource.Error -> {
                _state.update { it.copy(errorMessage = result.throwable?.message) }
                listOf(1)
            }

            is Resource.Success -> {
                //The default value is set to -1
                //If it -1, the user has not selected anything. We have to select something.
                if (result.data != null && !result.data.contains(-1)) {
                    Knower.d(
                        "PracticeComponent - readAndReturnSettingLevels",
                        "Here is the read settings level result ${result.data}"
                    )
                    result.data
                } else {
                    listOf(1)
                }
            }
        }
    }

    private suspend fun readSets(): List<VocabularySetModel>? {
        //Selects local sql sets. If it returns null, that means nothing was there.
        //If null, we will have to download a set from supabase
        return when (val result = repo.readVocabSets()) {
            is Resource.Error -> {
                Knower.e(
                    "PracticeComponent - readSets",
                    "An error occurred here: ${result.throwable?.message}"
                )
                null
            }

            is Resource.Success -> {
                if (!result.data.isNullOrEmpty()) {
                    result.data
                } else {
                    null
                }
            }
        }
    }

    private suspend fun selectAndReturnSets(sets: List<VocabularySetModel>): List<VocabularySetModel> {
        //We have a list of all the local sets
        //We then read the local settings for selected set ids
        //When we return an emptyList, that means nothing was selected, so save the first set's local id as the selected set.
        return when (val result = repo.readSelectedSetIds()) {
            is Resource.Error -> {
                Knower.e(
                    "PracticeComponent - selectAndReturnSets",
                    "An error occurred here: ${result.throwable?.message}"
                )
                if (sets.first().localId != null) {
                    repo.updateSelectedSets(listOf(sets.first().localId!!))
                    listOf(sets.first())
                } else {
                    emptyList()
                }
            }

            is Resource.Success -> {
                if (result.data != null && !result.data.contains(-1)) {
                    sets.filter { result.data.contains(it.localId) }
                } else {
                    if (sets.first().localId != null) {
                        repo.updateSelectedSets(listOf(sets.first().localId!!))
                        listOf(sets.first())

                    } else {
                        emptyList()
                    }
                }
            }
        }
    }

    private suspend fun getDefaultSetFromRemote(): VocabularySetModel? {
        //This will only be triggered if no local sets exist.
        //If an error occurs here, then there is nothing else that can be done.
        //That means it's client side error, network or something
        return when (val result = repo.getDefaultSet()) {
            is Resource.Error -> {
                Knower.e(
                    "PracticeComponent - readSets",
                    "An error occurred here: ${result.throwable?.message}"
                )
                null
            }

            is Resource.Success -> {
                if (result.data != null) {
                    result.data
                } else {
                    Knower.e(
                        "PracticeComponent - readSets",
                        "An error occurred here: ${result.throwable?.message}"
                    )
                    null
                }
            }
        }
    }

    private fun changeCurrentValues() {
        val totalCards = _state.value.selectedSet.flatMap { it.cards }
        val totalPoints = _state.value.selectedLevel.flatMap { it.grammarList }
        _state.update {
            it.copy(
                currentVocabulary = totalCards[returnRandomItem(totalCards.size)],
                currentGrammar = totalPoints[returnRandomItem(totalPoints.size)]
            )
        }
    }

    private fun returnRandomItem(size: Int): Int {
        return (0..size).random()
    }
}