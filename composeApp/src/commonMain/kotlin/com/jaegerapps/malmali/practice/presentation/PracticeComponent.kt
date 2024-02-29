package com.jaegerapps.malmali.practice.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.jaegerapps.malmali.grammar.models.GrammarLevel
import com.jaegerapps.malmali.login.domain.UserData
import com.jaegerapps.malmali.practice.domain.repo.PracticeRepo
import com.jaegerapps.malmali.practice.mappers.toUiHistoryItem
import com.jaegerapps.malmali.practice.mappers.toUiPracticeGrammarList
import com.jaegerapps.malmali.practice.mappers.toUiPracticeVocabList
import com.jaegerapps.malmali.practice.models.UiHistoryItem
import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel
import core.Knower
import core.Knower.d
import core.Knower.e
import core.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PracticeComponent(
    private val grammarLevel: List<GrammarLevel>,
    private val userData: UserData,
    private val vocabularySets: List<VocabSetModel>,
    private val onNavigate: (String) -> Unit,
    private val practiceRepo: PracticeRepo,
    componentContext: ComponentContext,
) : ComponentContext by componentContext {

    private val _state = MutableStateFlow(PracticeUiState())
    val state = combine(
        _state,
        practiceRepo.getHistorySql(),
    ) { state, history ->
        Knower.e("combine PracticeComponent", "Something changed. Here is the history: $history")

        _state.update {
            it.copy(
                history = history.map { it.toUiHistoryItem() }
            )
        }
        state.copy(
            history = history.map { it.toUiHistoryItem() }
        )
    }.stateIn(
        CoroutineScope(Dispatchers.Main),
        SharingStarted.WhileSubscribed(5000),
        PracticeUiState()
    )

    init {
        lifecycle.doOnCreate {
            val filteredLevels = grammarLevel.toUiPracticeGrammarList()
                .filter { level -> level.title in userData.selectedLevels }
            val filteredSets = vocabularySets.first { set ->
                set.title in userData.sets
            }.toUiPracticeVocabList()
            _state.update {
                Knower.d(
                    "PracticeComponent onCreate",
                    "Here is the value for vocabSets: ${vocabularySets}"
                )
                Knower.d(
                    "PracticeComponent onCreate",
                    "Here is the value for grammarLevel: ${grammarLevel}"
                )
                Knower.d(
                    "PracticeComponent onCreate", "Here is the value for vocabSets filtered: ${
                        vocabularySets.filter { set ->
                            set.title in userData.sets
                        }
                    }"
                )
                Knower.d(
                    "PracticeComponent onCreate",
                    "Here is the value for grammarLevel filtered: ${grammarLevel}.toUiPracticeGrammarList().filter { level -> level.title in userData.selectedLevels }"
                )
                it.copy(
                    grammarList = filteredLevels,
                    activeFlashcards = filteredSets,
                    currentGrammar = filteredLevels.first().grammar.first(),
                    currentVocabulary = filteredSets.first()
                )
            }
        }
    }

    init {
        lifecycle.doOnCreate {
            scope.launch {


            }

        }
    }

    val scope = CoroutineScope(Dispatchers.IO)


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
                    val newHistory = UiHistoryItem(
                        id = 0,
                        sentence = _state.value.text,
                        grammar = _state.value.currentGrammar!!,
                        vocab = _state.value.currentVocabulary!!,
                    )
                    scope.launch {
                        when (val result = practiceRepo.insertHistorySql(newHistory)) {
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
            PracticeUiEvent.RefreshPracticeContainer -> TODO()
        }
    }
}