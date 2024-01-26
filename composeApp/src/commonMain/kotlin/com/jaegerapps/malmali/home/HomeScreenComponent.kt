package com.jaegerapps.malmali.home

import com.arkivanov.decompose.ComponentContext
import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.login.data.UserDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class HomeScreenComponent(
    componentContext: ComponentContext,
    private val userDTO: UserDTO,
    private val onNavigate: (String) -> Unit,
) : ComponentContext by componentContext {

    /*TODO - Eventually will have to make a loading thing so we can load information
    *  from the online database*/
    private val _state = MutableStateFlow(HomeUiState())
    val state = _state

    init {
        _state.update {
            it.copy(
                userName = userDTO.user_nickname,
                icon = MR.images.cat_icon,
                userExperience = userDTO.user_experience.toLong(),
                currentLevel = userDTO.user_experience
            )
        }
    }

    fun onEvent(event: HomeScreenUiEvent) {
        when (event) {
            is HomeScreenUiEvent.OnNavigate -> {
                onNavigate(event.route)
            }
        }
    }
}