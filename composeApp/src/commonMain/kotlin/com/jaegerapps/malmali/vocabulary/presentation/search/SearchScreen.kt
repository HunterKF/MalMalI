package com.jaegerapps.malmali.vocabulary.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.common.components.ActionButton
import com.jaegerapps.malmali.common.components.CustomPopUp
import com.jaegerapps.malmali.vocabulary.presentation.components.FolderContainer
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun SearchScreen(
    component: SearchSetComponent,
) {
    val state = component.state.collectAsState()
    var expanded by remember { mutableStateOf(-1) }
    Column {
        if (state.value.showPopUp) {
            CustomPopUp(
                onDismissRequest = {
                    component.onEvent(SearchUiEvent.TogglePopUp)
                },
                title = stringResource(MR.strings.prompt_save),
                text = stringResource(MR.strings.popup_confirm_save_content),
                buttonContent = {
                    TextButton(
                        onClick = {
                            component.onEvent(SearchUiEvent.TogglePopUp)
                        }
                    ) {
                        Text(stringResource(MR.strings.prompt_cancel), color = MaterialTheme.colorScheme.outline)
                    }
                    TextButton(
                        onClick = {
                            component.onEvent(SearchUiEvent.SaveSet)
                        }
                    ) {
                        Text(stringResource(MR.strings.prompt_save), color = MaterialTheme.colorScheme.outline)
                    }
                }
            )
        }
        OutlinedTextField(
            value = state.value.searchText,
            onValueChange = {
                component.onEvent(SearchUiEvent.OnSearchTextValueChange(it))
            },
            placeholder = {
                Text(
                    text = "Search..."
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        component.onEvent(SearchUiEvent.SearchByTitle)
                    }
                ) {
                    Icon(
                        Icons.Default.Search,
                        "search"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    component.onEvent(SearchUiEvent.SearchByTitle)
                }
            ),
            singleLine = true
        )
        if (state.value.loading) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                itemsIndexed(state.value.sets) { index, set ->
                    FolderContainer(
                        title = set.title,
                        icon = painterResource(set.icon.resource),
                        expanded = expanded == index,
                        buttonContent = {
                            TextButton(
                                onClick = {
                                    component.onEvent(SearchUiEvent.SelectSet(set))
                                }
                            ) {
                                Text(stringResource(MR.strings.prompt_save), color = MaterialTheme.colorScheme.surface)
                            }
                        },
                        onEvent = {
                            expanded = if (expanded == index) {
                                -1
                            } else {
                                index
                            }
                        }
                    )
                }
            }
        }
    }

}