package com.jaegerapps.malmali.onboarding.personalization

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.components.ActionButton
import com.jaegerapps.malmali.components.IconContainer
import com.jaegerapps.malmali.components.blackBorder
import com.jaegerapps.malmali.components.models.IconResource
import com.jaegerapps.malmali.onboarding.welcome.components.SkipAndNextButton
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalizationScreen(
    component: PersonalizationComponent
) {
    var popUp by remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val state = component.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(state.value.error) {
        state.value.error?.let {
            snackbarHostState.showSnackbar(it)
            component.onEvent(PersonalizationUiEvents.OnErrorClear)
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize().padding(12.dp),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        if (state.value.selectIconPopUp) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.7f),
                onDismissRequest = {
                    popUp = false
                    scope.launch {
                        sheetState.hide()
                    }
                },
                sheetState = sheetState
            ) {
                LazyVerticalGrid(
                    modifier = Modifier.padding(12.dp),
                    columns = GridCells.Fixed(count = 4),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(IconResource.userIconList) {
                        IconContainer(
                            modifier = Modifier.aspectRatio(1f).clip(CircleShape)
                                .clickable { component.onEvent(PersonalizationUiEvents.OnIconChange(it)) },
                            icon = painterResource(it.resource),
                            contentDesc = it.tag
                        )
                    }

                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center.apply { Arrangement.spacedBy(32.dp) }
        ) {
            Spacer(Modifier.weight(1f))

            Text(
                text = "Account personalization"
            )
            Spacer(Modifier.weight(1f))

            Column(
                modifier = Modifier.clip(RoundedCornerShape(25.dp)).clickable {
                    scope.launch {
                        popUp = true
                        sheetState.show()
                    }
                }.fillMaxWidth(0.5f).padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconContainer(
                    size = 76.dp,
                    modifier = Modifier,
                    icon = if (state.value.selectedIcon?.resource != null ) painterResource(state.value.selectedIcon!!.resource) else painterResource(MR.images.user_icon_bear_1)
                )
                Spacer(Modifier.height(16.dp))
                Text("Select your avatar.")
            }
            Spacer(Modifier.weight(1f))
            OutlinedTextField(
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier.fillMaxWidth().blackBorder(),
                value = state.value.nickname,
                placeholder = {
                    Text("nickname")
                },
                onValueChange = {
                    component.onEvent(PersonalizationUiEvents.OnNicknameChange(it))
                },
                singleLine = true
            )
            Spacer(Modifier.weight(1f))

            ActionButton(
                text = "Next",
                onClick = {
                    popUp = !popUp
                }
            )
            Spacer(Modifier.weight(1f))

        }
    }
}