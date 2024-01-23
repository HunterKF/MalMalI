package com.jaegerapps.malmali.vocabulary.folders.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.jaegerapps.malmali.components.CustomNavigationDrawer
import com.jaegerapps.malmali.components.SettingsAndModal
import com.jaegerapps.malmali.components.TopBarLogo
import com.jaegerapps.malmali.navigation.FlashcardHomeComponent
import com.jaegerapps.malmali.vocabulary.domain.VocabSet
import com.jaegerapps.malmali.vocabulary.components.MultiFloatingActionButtons
import com.jaegerapps.malmali.vocabulary.components.FolderContainer
import com.jaegerapps.malmali.vocabulary.study_flashcards.StudyFlashcardsUiEvent
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.launch

@Composable
fun FolderScreen(
    component: FlashcardHomeComponent,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val state = component.state.collectAsState()
    var floatingButtonExpand by remember {
        mutableStateOf(false)
    }
    CustomNavigationDrawer(
        drawerState = drawerState,
        onNavigate = { route ->
            component.onEvent(FolderUiEvent.OnModalNavigate(route))
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                MultiFloatingActionButtons(
                    isExpanded = floatingButtonExpand,
                    onAddClick = {
                                 component.onEvent(FolderUiEvent.OnNavigateToCreateClick)
                    },
                    onSearchClick = {},
                    onExpandClick = {
                        floatingButtonExpand = !floatingButtonExpand
                    }
                )
            },
            topBar = {
                TopBarLogo {
                    Text("단어", color = MaterialTheme.colorScheme.onPrimary)
                }
            },
            containerColor = MaterialTheme.colorScheme.surface
        ) { paddingValues ->
            var isExpanded by remember { mutableStateOf(-1) }
            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(paddingValues)
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
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
                itemsIndexed(state.value.setList) { index, grammarSet ->
                    FolderContainer(
                        title = grammarSet.title,
                        icon = painterResource(grammarSet.icon),
                        onEvent = {
                            isExpanded = if (isExpanded != index) {
                                index
                            } else {
                                -1
                            }
                        },
                        expanded = isExpanded == index
                    ) {
                        TextButton(onClick = {
                            grammarSet.setId?.let { id ->
                                component.onEvent(
                                    FolderUiEvent.OnStudyClick(
                                        id,
                                        grammarSet.title,
                                        grammarSet.dateCreated
                                    )
                                )
                            }
                            println(grammarSet)
                        }) {
                            Text(
                                text = "Study",
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                        TextButton(onClick = {
                            if (grammarSet.setId != null) {
                                component.onEvent(
                                    FolderUiEvent.OnEditClick(
                                        grammarSet.title,
                                        grammarSet.setId,
                                        grammarSet.dateCreated
                                    )
                                )
                            }
                        }) {
                            Text(
                                text = "Edit",
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                        TextButton(onClick = {
                            grammarSet.setId?.let { id ->
                                component.onEvent(FolderUiEvent.OnShareClick(id))
                            }
                        }) {
                            Text(
                                text = "Share",
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                    }

                }
                item {
                    Spacer(Modifier.height(54.dp))
                }
            }
        }
    }
}

