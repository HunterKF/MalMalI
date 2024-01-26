package com.jaegerapps.malmali.login.presentation

import com.arkivanov.decompose.ComponentContext
import com.jaegerapps.malmali.login.domain.SignIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInComponent(
    componentContext: ComponentContext,
    private val signIn: SignIn,

    onNavigate: () -> Unit
) {
    private val _state = MutableStateFlow(SignInState())
    val state = _state

    private val scope = CoroutineScope(Dispatchers.IO)

    fun onEvent(event: SignInUiEvent) {
        when (event) {
            is SignInUiEvent.OnError -> {
                _state.update {
                    it.copy(
                        error = event.signInError
                    )
                }
            }
            is SignInUiEvent.SignInWithGmailSuccess -> {
                if (event.id != null && event.email != null ) {
                    scope.launch {
                        signIn.createUserLocally(userId = event.id, email = event.email)
                        signIn.createUserWithGmailExternally(event.id)
                        _state.update {
                            it.copy(
                                success = true
                            )
                        }
                    }
                } else {
                    _state.update {
                        it.copy(
                            error = SignInError.UNKNOWN_ERROR
                        )
                    }
                }


            }
            SignInUiEvent.ClearError -> {
                _state.update {
                    it.copy(
                        error = null
                    )
                }
            }

            SignInUiEvent.CreateAccountWithEmail ->{
                when {
                    _state.value.password != _state.value.retypePassword -> {
                        _state.update {
                            it.copy(
                                error = SignInError.PASSWORD_NOT_SAME
                            )
                        }
                        return
                    }
                    _state.value.email.isBlank() -> {
                        _state.update {
                            it.copy(
                                error = SignInError.EMAIL_BLANK
                            )
                        }
                        return

                    }
                    _state.value.password.isBlank()  || _state.value.retypePassword.isBlank()-> {
                        _state.update {
                            it.copy(
                                error = SignInError.PASSWORD_BLANK
                            )
                        }
                        return

                    }
                    _state.value.password.length < 6 -> {
                        _state.update {
                            it.copy(
                                error = SignInError.PASSWORD_TOO_SHORT
                            )
                        }
                        return
                    }
                    !isValidEmail(_state.value.email) -> {
                        _state.update {
                            it.copy(
                                error = SignInError.EMAIL_VALIDATION
                            )
                        }
                        return
                    }
                    else -> {
                        _state.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                        scope.launch {
                            signIn.createUserWithEmailExternally(_state.value.email, _state.value.password)
                        }
                    }
                }
                if (_state.value.password != _state.value.retypePassword) {
                    _state.update {
                        it.copy(
                            error = SignInError.PASSWORD_NOT_SAME
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                    scope.launch {
                        signIn.createUserWithEmailExternally(_state.value.email, _state.value.password)
                    }
                }

            }

            is SignInUiEvent.ChangeEmailValue -> {
                _state.update {
                    it.copy(
                        email = event.value
                    )
                }
            }
            is SignInUiEvent.ChangePasswordValue -> {
                _state.update {
                    it.copy(
                        password = event.value
                    )
                }
            }
            is SignInUiEvent.ChangeRetypePasswordValue -> {
                _state.update {
                    it.copy(
                        retypePassword = event.value
                    )
                }
            }

            SignInUiEvent.ToggleMode -> {
                _state.update {
                    it.copy(
                        mode = if (it.mode == SignInMode.SIGN_IN) SignInMode.ACCOUNT_CREATE else SignInMode.SIGN_IN
                    )
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"

        return email.matches(emailRegex.toRegex())
    }
}

