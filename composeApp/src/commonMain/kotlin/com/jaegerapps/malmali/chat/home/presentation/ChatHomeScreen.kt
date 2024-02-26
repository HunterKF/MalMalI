package com.jaegerapps.malmali.chat.home.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.components.CustomNavigationDrawer
import com.jaegerapps.malmali.components.IconContainer
import com.jaegerapps.malmali.components.SettingsAndModal
import com.jaegerapps.malmali.components.TopBarLogo
import com.jaegerapps.malmali.grammar.presentation.GrammarUiEvent
import com.jaegerapps.malmali.grammar.presentation.components.GrammarListContainer
import com.jaegerapps.malmali.vocabulary.components.MultiFloatingActionButtons
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.launch

@Composable
fun ChatHomeScreen(
    component: ChatHomeComponent
) {
    val state by component.state.collectAsState()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    CustomNavigationDrawer(
        drawerState = drawerState,
        onNavigate = { route ->
            component.onEvent(ChatHomeEvent.OnNavigate(route))
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),

            topBar = {
                TopBarLogo {
                    Text("이야기", color = MaterialTheme.colorScheme.onPrimary)
                }
            },
            containerColor = MaterialTheme.colorScheme.surface
        ) { paddingValues ->
            Column(
                modifier = Modifier.fillMaxWidth().padding(paddingValues)
                    .padding(horizontal = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
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
                Spacer(Modifier.height(18.dp))
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    columns = GridCells.Fixed(2)
                ) {
                    items(state.topics) { topic ->
                        Column(
                            modifier = Modifier.clickable {
                                component.onEvent(ChatHomeEvent.SelectTopic(topic))
                            },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            IconContainer(
                                icon = painterResource(MR.images.user_icon_bear_1)
                            )
                            Text(
                                text = topic.title
                            )
                        }

                    }

                    item {
                        Spacer(Modifier.height(36.dp))
                    }
                }

            }
        }
    }
}