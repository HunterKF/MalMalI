package com.jaegerapps.malmali.chat.conversation.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
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
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.chat.conversation.presentation.components.ChatAwaitAnimation
import com.jaegerapps.malmali.chat.conversation.presentation.components.ChatBubble
import com.jaegerapps.malmali.chat.conversation.presentation.components.ChatTextField
import com.jaegerapps.malmali.chat.home.presentation.ChatHomeEvent
import com.jaegerapps.malmali.chat.models.ConversationUi
import com.jaegerapps.malmali.components.CustomNavigationDrawer
import com.jaegerapps.malmali.components.SettingsAndModal
import kotlinx.coroutines.launch

@Composable
fun ConversationScreen(
    component: ConversationComponent,
) {
    val state by component.state.collectAsState()
//    val conversation = listOf(
//        ConversationUi(
//            id = 1,
//            role = "user",
//            content = "Hi, I'm having trouble accessing my account.",
//            selected = false
//        ),
//        ConversationUi(
//            id = 2,
//            role = "assistant",
//            content = "Hello! I'd be happy to help you. Could you provide me with your account email, please?",
//            selected = false
//        ),
//        ConversationUi(
//            id = 3,
//            role = "user",
//            content = "Sure, it's user@example.com.",
//            selected = false
//        ),
//        ConversationUi(
//            id = 4,
//            role = "assistant",
//            content = "Thank you. One moment while I check your account details.",
//            selected = false
//        ),
//        ConversationUi(
//            id = 5,
//            role = "assistant",
//            content = "It looks like there's a temporary block on your account due to suspicious activity. I can help you resolve this.",
//            selected = true
//        ),
//        ConversationUi(
//            id = 6,
//            role = "user",
//            content = "Oh, I see. What do I need to do to remove the block?",
//            selected = false
//        ),
//        ConversationUi(
//            id = 7,
//            role = "assistant",
//            content = "You'll need to verify your identity with a photo ID. Can you upload a photo ID through our secure form?",
//            selected = false
//        ),
//        ConversationUi(
//            id = 8,
//            role = "user",
//            content = "Yes, I can do that. Where can I find the form?",
//            selected = false
//        ),
//        ConversationUi(
//            id = 9,
//            role = "assistant",
//            content = "I'm sending you the link to the form now. [Link to form]",
//            selected = false
//        ),
//        ConversationUi(
//            id = 10,
//            role = "user",
//            content = "I've submitted the form with my ID.",
//            selected = false
//        ),
//        ConversationUi(
//            id = 11,
//            role = "assistant",
//            content = "Great, your identity has been verified, and I've removed the block. You should be able to access your account now.",
//            selected = true
//        ),
//        ConversationUi(
//            id = 12,
//            role = "user",
//            content = "Thank you so much for your help!",
//            selected = false
//        ),
//        ConversationUi(
//            id = 13,
//            role = "assistant",
//            content = "You're welcome! If you have any more questions or need further assistance, feel free to contact us. Have a great day!",
//            selected = false
//        )
//    )

    var isSelected by remember {
        mutableStateOf(-1)
    }
    val scrollState = rememberLazyListState()
    LaunchedEffect(Unit) {
        if (state.conversation.size > 4) {
            scrollState.animateScrollToItem(state.conversation.size - 1)
        }
    }
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    CustomNavigationDrawer(
        drawerState = drawerState,
        onNavigate = { route ->
            component.onEvent(ConversationUiEvent.OnNavigate(route))
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                Box(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.outline)) {

                    SettingsAndModal(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        color = MaterialTheme.colorScheme.surface,
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
            },
            bottomBar = {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.outline).padding(12.dp).fillMaxWidth()
                ) {

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
        ) { paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {


                LazyColumn(
                    modifier = Modifier.padding(paddingValues).padding(horizontal = 12.dp),
                    state = scrollState,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    item {
                        Spacer(Modifier.height(12.dp))
                    }
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
                    item {
                        Spacer(Modifier.height(12.dp))
                    }

                }


            }
        }
    }
}