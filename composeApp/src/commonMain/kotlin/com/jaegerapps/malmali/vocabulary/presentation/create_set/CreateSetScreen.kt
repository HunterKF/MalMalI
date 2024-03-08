package com.jaegerapps.malmali.vocabulary.presentation.create_set

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.components.ActionButton
import com.jaegerapps.malmali.components.CustomNavigationDrawer
import com.jaegerapps.malmali.components.CustomPopUp
import com.jaegerapps.malmali.components.SettingsAndModal
import com.jaegerapps.malmali.components.TopBarLogo
import com.jaegerapps.malmali.vocabulary.presentation.components.AddCardButton
import com.jaegerapps.malmali.vocabulary.presentation.components.EditVocabContainer
import com.jaegerapps.malmali.vocabulary.presentation.components.SelectIcon
import com.jaegerapps.malmali.vocabulary.presentation.components.SelectableButton
import com.jaegerapps.malmali.vocabulary.presentation.components.TitleBox
import core.Knower
import core.Knower.d
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateSetScreen(
    component: CreateSetComponent,
) {
    val state = component.state.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    LaunchedEffect(key1 = state.value.error) {
        Knower.d("CreateSetScreen", "The LaunchedEffect is being called.")
        val message = when (state.value.error) {
            UiError.MISSING_WORD -> "Some of the words are blank..."
            UiError.UNKNOWN_ERROR -> "Whoops, not sure what went wrong."
            UiError.ADD_CARDS -> "Add some vocabulary before saving."
            UiError.MISSING_TITLE -> "Please check the title."
            null -> {
                ""
            }
        }
        if (state.value.error != null) {
            snackbarHostState.showSnackbar(message)
            component.onEvent(CreateSetUiEvent.OnClearUiError)
        }
    }
    CustomNavigationDrawer(
        drawerState = drawerState,
        onNavigate = { route ->
            component.onEvent(CreateSetUiEvent.OnModalNavigate(route))
        }
    ) {

        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            modifier = Modifier.fillMaxSize(), topBar = {
                TopBarLogo {
                    Text("단어", color = MaterialTheme.colorScheme.onPrimary)
                }
            },
            containerColor = MaterialTheme.colorScheme.surface
        ) { paddingValues ->

            if (state.value.showSavePopUp && state.value.error == null) {
                CustomPopUp(
                    onDismissRequest = {
                        component.onEvent(CreateSetUiEvent.TogglePopUp(PopUpMode.DISMISS))
                    },
                    title = if (state.value.mode == PopUpMode.SAVE) stringResource(MR.strings.popup_confirm_save_title) else stringResource(
                        MR.strings.popup_confirm_delete_title
                    ),
                    text = if (state.value.mode == PopUpMode.SAVE) stringResource(MR.strings.popup_confirm_save_content) else stringResource(
                        MR.strings.popup_confirm_delete_title
                    )
                ) {
                    TextButton(
                        onClick = {
                            component.onEvent(CreateSetUiEvent.TogglePopUp(PopUpMode.DISMISS))
                        }
                    ) {
                        Text(stringResource(MR.strings.prompt_cancel))
                    }
                    TextButton(
                        onClick = {
                            component.onEvent(CreateSetUiEvent.ConfirmPopUp)
                        }
                    ) {
                        Text(
                            text = if (state.value.mode == PopUpMode.SAVE) stringResource(MR.strings.prompt_save) else stringResource(
                                MR.strings.prompt_delete
                            )
                        )
                    }
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(paddingValues)
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    SettingsAndModal(
                        onSettingsClick = {},
                        onModalClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }
                    )
                }

                item {
                    TitleBox(
                        value = state.value.title,
                        onValueChange = { component.onEvent(CreateSetUiEvent.EditTitle(it)) },
                        isError = state.value.error == UiError.MISSING_TITLE
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SelectableButton(
                            text = "Private",
                            isSelected = state.value.isPublic == false,
                            onClick = {
                                component.onEvent(CreateSetUiEvent.ChangePublicSetting(false))
                            }
                        )
                        SelectableButton(
                            text = "Public",
                            isSelected = state.value.isPublic == true,
                            onClick = {
                                component.onEvent(CreateSetUiEvent.ChangePublicSetting(true))
                            }
                        )
                    }
                }
                item {
                    state.value.icon?.let {
                        SelectIcon(
                            defaultIcon = painterResource(it.resource),
                            onClick = {
                                component.onEvent(CreateSetUiEvent.OpenIconSelection)
                            }
                        )
                    }
                }
                itemsIndexed(state.value.vocabularyCardModels.filter { it.uiId != null }) { index, card ->
                    EditVocabContainer(
                        word = card.word,
                        def = card.definition,
                        isError = card.error,
                        onWordChange = {
                            component.onEvent(CreateSetUiEvent.EditWord(card.uiId!!, text = it))
                        },
                        onDefChange = {
                            component.onEvent(CreateSetUiEvent.EditDef(card.uiId!!, it))
                        },
                        onDelete = {
                            component.onEvent(CreateSetUiEvent.DeleteWord(index))
                        },
                        onClearError = {
                            component.onEvent(CreateSetUiEvent.OnClearErrorVocab(card.uiId!!))
                        }
                    )
                }
                item {
                    AddCardButton(
                        text = "Add Card",
                        onClick = { component.onEvent(CreateSetUiEvent.AddCard) },
                        onLongClick = { component.onEvent(CreateSetUiEvent.AddCards) }
                    )
                }
                item {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ActionButton(
                                modifier = Modifier.weight(1f),
                                backgroundColor = MaterialTheme.colorScheme.error,
                                text = "Delete",
                                onClick = { component.onEvent(CreateSetUiEvent.TogglePopUp(PopUpMode.DELETE)) }
                            )
                            Spacer(Modifier.width(16.dp))
                            ActionButton(
                                modifier = Modifier.weight(1f),
                                text = stringResource(MR.strings.prompt_save),
                                onClick = { component.onEvent(CreateSetUiEvent.TogglePopUp(PopUpMode.SAVE)) }
                            )
                        }
                        Spacer(Modifier.height(56.dp))
                    }
                }
            }
        }
    }
}