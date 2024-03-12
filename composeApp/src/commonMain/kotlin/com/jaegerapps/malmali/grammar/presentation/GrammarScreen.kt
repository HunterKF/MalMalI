package com.jaegerapps.malmali.grammar.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.common.components.CustomNavigationDrawer
import com.jaegerapps.malmali.common.components.SettingsAndModal
import com.jaegerapps.malmali.common.components.TopBarLogo
import com.jaegerapps.malmali.grammar.presentation.components.GrammarContainer
import com.jaegerapps.malmali.grammar.presentation.components.GrammarListContainer
import com.jaegerapps.malmali.vocabulary.presentation.components.MultiFloatingActionButtons
import kotlinx.coroutines.launch

@Composable
fun GrammarScreen(
    component: GrammarScreenComponent,
    backgroundColor: Color = MaterialTheme.colorScheme.outline,
    foregroundColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val state by component.state.collectAsState()

    LaunchedEffect(state.error) {
        if (state.error != null) {
            snackbarHostState.showSnackbar(state.error!!)
            component.onEvent(GrammarUiEvent.ClearError)
        }
    }

    var floatingButtonExpand by remember {
        mutableStateOf(false)
    }
    CustomNavigationDrawer(
        drawerState = drawerState,
        onNavigate = { route ->
            component.onEvent(GrammarUiEvent.OnNavigate(route))
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                MultiFloatingActionButtons(
                    isExpanded = floatingButtonExpand,

                    onExpandClick = {
                        floatingButtonExpand = !floatingButtonExpand
                    },
                    buttonContent = {
                        IconButton(
                            modifier = Modifier.size(48.dp).clip(
                                CircleShape
                            ),
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = backgroundColor,
                                contentColor = foregroundColor
                            ),
                            onClick = {
                                component.onEvent(GrammarUiEvent.ToggleEditMode)
                            }
                        ) {
                            Icon(
                                Icons.Rounded.Edit,
                                null
                            )
                        }
                    }
                )
            },
            topBar = {
                TopBarLogo {
                    Text("문법", color = MaterialTheme.colorScheme.onPrimary)
                }
            },
            containerColor = MaterialTheme.colorScheme.surface
        ) { paddingValues ->
            Column(
                modifier = Modifier.fillMaxWidth().padding(paddingValues)
                    .padding(horizontal = 12.dp)
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
                GrammarListContainer(
                    isEditingMode = state.isEditing,
                    levels = state.grammarLevelModelList,
                    onSelect = {
                        component.onEvent(GrammarUiEvent.ToggleLevelSelection(it))
                    }
                )
                var expanded by remember {
                    mutableStateOf(0)
                }
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(state.grammarLevelModelList) { index, level ->
                        GrammarContainer(
                            grammarLevelModel = level,
                            isExpanded = index == expanded,
                            isEditingMode = state.isEditing,
                            onExpandClick = {
                                if (expanded != index) {
                                    expanded = index
                                } else {
                                    expanded = -1
                                }
                            },
                            onSelectClick = {
                                component.onEvent(GrammarUiEvent.ToggleLevelSelection(level))
                            }
                        )
                    }
                    item {
                        Spacer(Modifier.height(56.dp))
                    }

                }



            }
        }
    }
}
