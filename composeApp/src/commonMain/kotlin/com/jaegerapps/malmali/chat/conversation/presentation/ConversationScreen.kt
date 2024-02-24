package com.jaegerapps.malmali.chat.conversation.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.chat.conversation.presentation.components.ChatAwaitAnimation
import com.jaegerapps.malmali.chat.conversation.presentation.components.ChatBubble
import com.jaegerapps.malmali.chat.conversation.presentation.components.ChatTextField
import com.jaegerapps.malmali.chat.models.ConversationUi
import com.jaegerapps.malmali.components.SettingsAndModal

@Composable
fun ConversationScreen(
    component: ConversationComponent,
) {
    val state by component.state.collectAsState()
    var isSelected by remember {
        mutableStateOf(-1)
    }
    val scrollState = rememberLazyListState()
    LaunchedEffect(Unit) {
        if (state.conversation.size > 4) {
            scrollState.animateScrollToItem(state.conversation.size - 1)
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)
        ) {
            SettingsAndModal(
                onSettingsClick = {},
                onModalClick = {}
            )
            LazyColumn(
                state = scrollState,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                itemsIndexed(state.conversation) { index, conversation ->
                    ChatBubble(
                        text = conversation.content,
                        isUser = conversation.role == "user",
                        showOptions = isSelected == index,
                        onClick = {
                            isSelected = if (isSelected != index) {
                                index
                            } else {
                                -1
                            }
                        },
                    )
                }
                item {
                    if (state.waitingResponse) {
                        ChatAwaitAnimation()
                    }
                }
            }
            Spacer(Modifier.height(18.dp))
            ChatTextField(
                text = state.text,
                onValueChange = {
                    component.onEvent(ConversationUiEvent.OnTextChange(it))
                },
                onFinish = {
                    component.onEvent(ConversationUiEvent.SendChant)
                },
                micMode = false,
                recording = false
            )
            Spacer(Modifier.height(18.dp))

        }
    }
}