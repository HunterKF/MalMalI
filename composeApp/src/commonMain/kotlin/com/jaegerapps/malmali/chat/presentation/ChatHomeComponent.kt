package com.jaegerapps.malmali.chat.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.jaegerapps.malmali.chat.domain.ChatRepo
import core.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatHomeComponent(
    private val onModalNavigate: (String) -> Unit,
    private val chatRepo: ChatRepo,
    componentContext: ComponentContext,
) : ComponentContext by componentContext {

    private val _state = MutableStateFlow(ChatHomeUiState())
    val state = _state
    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        lifecycle.doOnCreate {
            scope.launch {
                when (val result = chatRepo.getTopics()) {
                    is Resource.Error -> {
                        withContext(Dispatchers.Main) {
                            result.throwable?.let { throwable ->
                                _state.update {
                                    it.copy(
                                        error = throwable.message ?: "Unknown error"
                                    )
                                }
                            }

                        }
                    }

                    is Resource.Success -> {
                        withContext(Dispatchers.Main) {
                            result.data?.let { topics ->
                                _state.update {
                                    it.copy(
                                        topics = topics
                                    )
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: ChatHomeEvent) {
        when (event) {
            is ChatHomeEvent.OnNavigate -> {
                onModalNavigate(event.route)
            }
            is ChatHomeEvent.SelectTopic -> {
                _state.update {
                    it.copy(
                        selectedTopic = event.topic
                    )
                }

            }
        }

    }
}