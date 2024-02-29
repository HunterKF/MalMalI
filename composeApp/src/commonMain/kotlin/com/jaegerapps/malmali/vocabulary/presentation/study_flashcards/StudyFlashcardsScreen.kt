package com.jaegerapps.malmali.vocabulary.presentation.study_flashcards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.components.CustomPopUp
import com.jaegerapps.malmali.components.CustomNavigationDrawer
import com.jaegerapps.malmali.components.SettingsAndModal
import com.jaegerapps.malmali.components.TopBarLogo
import com.jaegerapps.malmali.vocabulary.presentation.components.FolderContainer
import com.jaegerapps.malmali.vocabulary.presentation.study_flashcards.components.VocabularyButtons
import com.jaegerapps.malmali.vocabulary.presentation.study_flashcards.components.VocabularyContainer
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch

@Composable
fun StudyFlashcardsScreen(
    component: StudyFlashcardsComponent,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val state = component.state.collectAsState()

    var expanded by remember {
        mutableStateOf(false)
    }
    CustomNavigationDrawer(
        drawerState = drawerState,
        onNavigate = { route ->
            component.onEvent(StudyFlashcardsUiEvent.OnModalNavigate(route))
        }
    ) {
        if (state.value.isComplete) {
            CustomPopUp(
                onDismissRequest = {
//                    component.onEvent(StudyFlashcardsUiEvent.OnBackClick)
                },
                title = stringResource(MR.strings.popup_set_complete_title),
                text = "",
                contentText = {
                    Column {
                        Text("stringResource(MR.strings.popup_set_complete_cards_reviewed)")
                        Text("stringResource(MR.strings.popup_set_complete_exp_gained)")
                    }
                }
            ) {
                TextButton(
                    onClick = {
                        component.onEvent(StudyFlashcardsUiEvent.OnRepeatClick)
                    }
                ) {
                    Text(stringResource(MR.strings.prompt_repeat))
                }
                TextButton(
                    onClick = {
                        component.onEvent(StudyFlashcardsUiEvent.OnCompleteClick)
                    }
                ) {
                    Text(stringResource(MR.strings.prompt_back))
                }
            }
        }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.surface,
            topBar = {
                TopBarLogo {
                    Text("단어", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues = paddingValues)
                    .padding(horizontal = 12.dp)
            ) {
                SettingsAndModal(
                    onSettingsClick = {
                        println("Hello~~")
                    },
                    onModalClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
                state.value.set?.let {

                    FolderContainer(
                        title = state.value.set?.title ?: "error",
                        icon = painterResource(it.icon.resource),
                        onEvent = {
                            component.onEvent(StudyFlashcardsUiEvent.OnFolderClick(onClick = {
                                expanded = !expanded
                            }))
                        },
                        expanded = expanded
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            TextButton(
                                onClick = {
                                    component.onEvent(StudyFlashcardsUiEvent.OnSetEditClick)
                                }
                            ) {
                                Text(
                                    text = stringResource(MR.strings.prompt_edit),
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                            TextButton(
                                onClick = {
                                    component.onEvent(StudyFlashcardsUiEvent.OnSetShareClick)
                                }
                            ) {
                                Text(
                                    text = stringResource(MR.strings.prompt_share),
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.height(34.dp))
                state.value.currentCard?.let { card ->
                    VocabularyContainer(
                        modifier = Modifier.weight(1f),
                        setSize = state.value.set!!.cards.size,
                        currentIndex = state.value.currentIndex + 1,
                        card = card,
                        showBack = state.value.showBack,
                        onClick = {
                            component.onEvent(it)
                        }
                    )
                }
                Spacer(Modifier.height(34.dp))

                VocabularyButtons {
                    component.onEvent(it)
                }
                Spacer(Modifier.height(34.dp))

            }
        }
    }
}