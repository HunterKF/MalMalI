package com.jaegerapps.malmali.practice.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
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
import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.chat.conversation.presentation.components.ChatBubble
import com.jaegerapps.malmali.components.ActionButton
import com.jaegerapps.malmali.components.CustomNavigationDrawer
import com.jaegerapps.malmali.components.SettingsAndModal
import com.jaegerapps.malmali.home.HomeScreenUiEvent
import com.jaegerapps.malmali.practice.models.UiPracticeGrammar
import com.jaegerapps.malmali.practice.models.UiPracticeVocab
import com.jaegerapps.malmali.practice.presentation.components.PracticeContainer
import com.jaegerapps.malmali.practice.presentation.components.PracticeTextField
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PracticeScreen() {

    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val controller = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
//    val state by component.state.collectAsState()

    CustomNavigationDrawer(
        drawerState = drawerState,
        onNavigate = { route ->
//            component.onEvent(HomeScreenUiEvent.OnNavigate(route))
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(it),
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
                var grammarExpand by remember { mutableStateOf(false) }
                var vocabExpand by remember { mutableStateOf(false) }
                PracticeContainer(
                    modifier = Modifier.onFocusChanged {
                        if (it.hasFocus) {

                        }

                    },
                    vocab = UiPracticeVocab(word = "가다", "to go, to move"),
                    grammar = UiPracticeGrammar(
                        grammar = "(으)면",
                        definition1 = "To indicate that one action occurs 'when or if' another action (that hasn't happened yet) occurs",
                        definition2 = null,
                        level = "Level 1"
                    ),
                    vocabExpanded = vocabExpand,
                    grammarExpanded = grammarExpand,
                    onClick = {
                        if (grammarExpand) {
                            grammarExpand = false
                            vocabExpand = true
                        } else if (vocabExpand) {
                            grammarExpand = true
                            vocabExpand = false
                        } else {
                            vocabExpand = true
                        }
                    }
                )

                PracticeTextField(
                    value = "",
                    onValueChange = {

                    }
                )
                ActionButton(
                    onClick = {

                    },
                    text = stringResource(MR.strings.prompt_save)
                )

                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(10) {
                        ChatBubble(
                            text = "Practice text $it",
                            showOptions = false,
                            onClick = {

                            },
                            isUser = false
                        )
                    }
                }

            }
        }
    }
}