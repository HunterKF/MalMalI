package com.jaegerapps.malmali.practice.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.chat.conversation.presentation.components.ChatBubble
import com.jaegerapps.malmali.chat.conversation.presentation.components.ChatPopUpAlert
import com.jaegerapps.malmali.components.ActionButton
import com.jaegerapps.malmali.components.CustomNavigationDrawer
import com.jaegerapps.malmali.components.SettingsAndModal
import com.jaegerapps.malmali.components.blackBorder
import com.jaegerapps.malmali.home.HomeScreenUiEvent
import com.jaegerapps.malmali.practice.models.UiPracticeGrammar
import com.jaegerapps.malmali.practice.models.UiPracticeVocab
import com.jaegerapps.malmali.practice.presentation.components.PracticeContainer
import com.jaegerapps.malmali.practice.presentation.components.PracticeTextField
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PracticeScreen(
    component: PracticeComponent
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val state by component.state.collectAsState()

    CustomNavigationDrawer(
        drawerState = drawerState,
        onNavigate = { route ->
            component.onEvent(PracticeUiEvent.OnNavigate(route))
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) { paddingValue ->
            Column(
                modifier = Modifier.fillMaxWidth().padding(paddingValue).padding(horizontal = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SettingsAndModal(
                    onSettingsClick = {

                    },
                    onModalClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
                var expanded by remember { mutableStateOf(-1) }
                val list = listOf("1", "2", "3", "4", "5")
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        if (state.currentVocabulary != null && state.currentGrammar != null) {
                            PracticeContainer(
                                vocab = state.currentVocabulary!!,
                                grammar = state.currentGrammar!!,
                                vocabExpanded = state.isVocabularyExpand,
                                grammarExpanded = state.isGrammarExpanded,
                                onClick = {
                                    component.onEvent(it)
                                }
                            )
                        }


                    }
                    item {
                        PracticeTextField(
                            value = state.text,
                            onValueChange = {
                                component.onEvent(PracticeUiEvent.OnValueChanged(it))
                            }
                        )
                    }
                    item {
                        ActionButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                component.onEvent(PracticeUiEvent.SavePractice)
                            },
                            text = stringResource(MR.strings.prompt_save)
                        )
                    }


                    itemsIndexed(state.history.asReversed()) { index, string ->
                        HistoryContainer(
                            text = "Practice text ${string.sentence}",
                            showOptions = expanded == index,
                            toggleExpand = {
                                expanded = if (expanded != index) {
                                    index
                                } else {
                                    -1
                                }
                            },
                            onClick = {
                                component.onEvent(it)
                            },
                        )
                    }
                    item {
                        Spacer(Modifier.height(36.dp))
                    }
                }


            }
        }
    }
}

@Composable
fun HistoryContainer(
    modifier: Modifier = Modifier,
    text: String,
    showOptions: Boolean,
    toggleExpand: () -> Unit,
    onClick: (PracticeUiEvent) -> Unit,
) {
    Column(
        modifier.fillMaxWidth().blackBorder().clickable { toggleExpand() }.padding(12.dp),
    ) {
        Text(
            text = text
        )
        AnimatedVisibility(showOptions) {
            Spacer(Modifier.height(12.dp))
            ChatPopUpAlert(
                modifier = Modifier.fillMaxWidth(),
                horizontal = Arrangement.SpaceEvenly,
                onFavoriteClick = {},
                onShareClick = {},
                onInformationClick = {},
                onCopyClick = {}
            )
        }


    }
}
