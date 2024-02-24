package com.jaegerapps.malmali.chat.conversation.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.jaegerapps.malmali.chat.domain.ChatRepo
import com.jaegerapps.malmali.chat.models.ConversationDTO
import com.jaegerapps.malmali.chat.models.ConversationUi
import com.jaegerapps.malmali.chat.models.TopicPromptDTO
import com.jaegerapps.malmali.chat.models.UiTopicPrompt
import com.jaegerapps.malmali.components.models.IconResource
import com.jaegerapps.malmali.login.domain.UserData
import core.Knower
import core.Knower.d
import core.Knower.e
import core.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConversationComponent(
    private val onNavigate: (String) -> Unit,
    private val userData: UserData,
    private val api: ChatRepo,
    private val topic: String,
    private val topicBackground: String,
    private val topicIcon: String,
    componentContext: ComponentContext,
) : ComponentContext by componentContext {

    private val _state = MutableStateFlow(ConversationUiState())
    val state = _state

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        lifecycle.doOnCreate {
            _state.update {
                it.copy(
                    waitingResponse = true,
                    user = userData
                )
            }
            scope.launch {
                val topic = UiTopicPrompt(
                    title = topic,
                    background = topicBackground,
                    icon = IconResource.resourceFromTag(topicIcon)
                )
                when (val result =
                    async { api.startConversation(topic, userData.nickname) }.await()) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = result.throwable?.message ?: "Unknown error occurred.",
                                waitingResponse = false
                            )
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { conversation ->
                            _state.update {
                                it.copy(
                                    conversation = addToList(conversation),
                                    waitingResponse = false
                                )
                            }
                        }
                    }
                }

            }
        }
    }

    fun onEvent(event: ConversationUiEvent) {
        when (event) {
            is ConversationUiEvent.OnNavigate -> {
                onNavigate(event.route)
            }

            is ConversationUiEvent.OnTextChange -> {
                _state.update {
                    it.copy(
                        text = event.value
                    )
                }
            }

            ConversationUiEvent.SendChant -> {

                _state.update {
                    it.copy(
                        waitingResponse = true,
                        conversation = addToList(
                            ConversationUi(
                                id = _state.value.conversation.size + 1,
                                content = _state.value.text,
                                selected = false,
                                role = "user"
                            )
                        ),
                        text = ""
                    )
                }
                scope.launch {
                    when (val result = async {
                        api.sendConversation(
                            _state.value.text,
                            history = _state.value.conversation
                        )
                    }.await()) {
                        is Resource.Error -> {
                            Knower.e("OnSend", "An error occurred. ${result.throwable?.message}")
                            _state.update {
                                it.copy(
                                    waitingResponse = false,
                                    error = result.throwable?.message,
                                    text = "ERROR"

                                )
                            }
                        }

                        is Resource.Success -> {
                            Knower.d("OnSend", "A result happened! :D ${result.data}")

                            result.data?.let { conversation ->
                                _state.update {
                                    it.copy(
                                        waitingResponse = false,
                                        conversation = addToList(conversation),
                                        text = ""
                                    )
                                }
                            }

                        }
                    }

                }
            }

            ConversationUiEvent.ErrorClear -> {
                _state.update {
                    it.copy(
                        error = null
                    )
                }
            }
        }
    }

    private fun addToList(conversation: ConversationUi): List<ConversationUi> {

        return _state.value.conversation.plus(conversation)
    }
}