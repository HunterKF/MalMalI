package com.jaegerapps.malmali.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Message
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.common.components.CustomNavigationDrawer
import com.jaegerapps.malmali.common.models.Routes
import com.jaegerapps.malmali.common.components.SettingsAndModal
import com.jaegerapps.malmali.common.components.TopBarLogo
import com.jaegerapps.malmali.home.components.CardButton
import com.jaegerapps.malmali.home.components.LevelBar
import com.jaegerapps.malmali.home.components.UserIcon
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    component: HomeScreenComponent,
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val state by component.state.collectAsState()

    CustomNavigationDrawer(
        drawerState = drawerState,
        onNavigate = { route ->
            component.onEvent(HomeScreenUiEvent.OnNavigate(route))
        }
    ) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            modifier = Modifier.fillMaxSize(), topBar = {
                TopBarLogo {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 28.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            modifier = Modifier.size(50.dp),
                            painter = painterResource(MR.images.icon_flower),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.background
                        )
                        Text(
                            text = "말말이",
                            color = MaterialTheme.colorScheme.background,
                            fontSize = 34.sp
                        )
                        Icon(
                            modifier = Modifier.size(50.dp),
                            painter = painterResource(MR.images.icon_flower),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.background

                        )
                    }
                }
            },
            containerColor = MaterialTheme.colorScheme.surface
        ) { paddingValue ->
            Column(
                modifier = Modifier.fillMaxWidth().padding(paddingValue)
                    .padding(horizontal = 12.dp),
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
                Text(
                    text = "환영합니다, ${state.userName}!"
                )

                UserIcon(
                    modifier = Modifier.padding(vertical = 18.dp),
                    icon = MR.images.cat_icon
                )
                LevelBar(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    startLevel = state.currentLevel,
                    endLevel = state.currentLevel + 1,
                    experience = state.userExperience.toInt(),
                    gaugeLocation = calculateProgress(
                        currentExperience = state.userExperience.toInt(),
                        experienceRequiredForNextLevel = 250,
                        experienceAtBaseLevel = 0
                    ),
                )
                Spacer(Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CardButton(
                        modifier = Modifier.weight(1f),
                        icon = MR.images.cat_icon,
                        vector = Icons.Rounded.Message,
                        text = "얘\n기",
                        leftSide = true,
                        onClick = {
                            component.onEvent(HomeScreenUiEvent.OnNavigate(Routes.CHAT))
                        }
                    )
                    Spacer(Modifier.weight(0.7f))
                    CardButton(
                        modifier = Modifier.weight(1f),
                        icon = MR.images.icon_pencil,
                        vector = null,
                        text = "연\n습",
                        leftSide = false,
                        onClick = {
                            component.onEvent(HomeScreenUiEvent.OnNavigate(Routes.PRACTICE))
                        }
                    )
                }
                Spacer(Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CardButton(
                        modifier = Modifier.weight(1f),
                        icon = MR.images.icon_flashcards,
                        vector = null,
                        text = "단\n어",
                        leftSide = true,
                        onClick = {
                            component.onEvent(HomeScreenUiEvent.OnNavigate(Routes.VOCABULARY))

                        }
                    )
                    Spacer(Modifier.weight(0.7f))
                    CardButton(
                        modifier = Modifier.weight(1f),
                        icon = MR.images.icon_grammar,
                        text = "문\n법",
                        leftSide = false,
                        onClick = {
                            component.onEvent(HomeScreenUiEvent.OnNavigate(Routes.GRAMMAR))
                        }
                    )
                }
            }
        }
    }
}

fun calculateProgress(
    currentExperience: Int,
    experienceRequiredForNextLevel: Int,
    experienceAtBaseLevel: Int,
): Float {
    return (currentExperience - experienceAtBaseLevel).toFloat() / (experienceRequiredForNextLevel - experienceAtBaseLevel)
}