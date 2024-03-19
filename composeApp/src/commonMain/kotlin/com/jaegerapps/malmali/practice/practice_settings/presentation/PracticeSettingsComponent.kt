package com.jaegerapps.malmali.practice.practice_settings.presentation

import com.arkivanov.decompose.ComponentContext
import com.jaegerapps.malmali.common.models.GrammarLevelModel
import com.jaegerapps.malmali.practice.practice_settings.domain.models.PracticeLevelModel
import com.jaegerapps.malmali.practice.practice_settings.domain.repo.PracticeSettingsRepo
import core.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch

class PracticeSettingsComponent(
    private val repo: PracticeSettingsRepo,
    levels: List<GrammarLevelModel>,
    componentContext: ComponentContext,
    private val onNavigate: () -> Unit,
) : ComponentContext by componentContext {

    private val _state = MutableStateFlow(PracticeSettingUiState())
    val state = _state.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.IO)
    init {
        scope.launch {
            val levelIds = repo.readSelectedLevels()
            val setIds = repo.readSelectedSetIds()
            when (val result = repo.readVocabSets()) {
                is Resource.Error -> _state.update { it.copy(scaffoldMessage = "Help me...") }
                is Resource.Success -> {
                    if (result.data != null) {
                        _state.update {
                            it.copy(
                                levels = levels.map { level -> PracticeLevelModel(level, levelIds.data?.contains(level.id)) },
                            )
                        }
                    }
                }
            }
        }
    }
    fun onEvent(event: PracticeSettingsUiEvent) {
        when (event) {
            PracticeSettingsUiEvent.ClearMessage -> {
                _state.update { it.copy(scaffoldMessage = null) }
            }

            is PracticeSettingsUiEvent.ToggleLevel -> {
                val selectedLevels =
                    _state.value.levels.map { if (it == event.level) it.copy(isSelected = true) else it }
                scope.launch {
                    val result = _state.updateAndGet {
                        it.copy(
                            levels = selectedLevels
                        )
                    }
                    repo.updateSelectedLevels(result.levels.filter { it.isSelected }
                        .map { it.levelModel.id })
                }
            }

            is PracticeSettingsUiEvent.ToggleSet -> {
                val selectedSets =
                    _state.value.sets.map { if (it == event.set) it.copy(isSelected = true) else it }
                scope.launch {
                    val result = _state.updateAndGet {
                        it.copy(
                            sets = selectedSets
                        )
                    }
                    repo.updateSelectedLevels(result.sets.filter { it.isSelected }
                        .map { it.set.localId!! })
                }
            }

            is PracticeSettingsUiEvent.ToggleTeacherOn -> {
                val result = _state.updateAndGet {
                    it.copy(
                        enableTeacher = event.value
                    )
                }
                scope.launch {
                    repo.toggleTeacherSetting(result.enableTeacher)
                }

            }

            is PracticeSettingsUiEvent.ToggleTranslationOn -> {
                val result = _state.updateAndGet {
                    it.copy(
                        enableTranslation = event.value
                    )
                }
                scope.launch {
                    repo.toggleTranslationSetting(result.enableTeacher)
                }
            }
            PracticeSettingsUiEvent.OnBackNavigate -> {
                onNavigate()
            }
        }
    }
}