package com.jaegerapps.malmali.login.presentation

import com.arkivanov.decompose.ComponentContext
import com.jaegerapps.malmali.login.domain.SignInRepo
import core.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInComponent(
    componentContext: ComponentContext,
    private val signIn: SignInRepo,
    private val onNavigate: () -> Unit,
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
                if (event.id != null && event.email != null) {
                    scope.launch {
                        when (val result = signIn.createUserWithGmailExternally(event.id)) {
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        error = SignInError.UNKNOWN_ERROR
                                    )
                                }

                                println("An error occurred! ${result.throwable}")
                            }

                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        loginSuccess = true
                                    )
                                }
                                onNavigate()
                            }
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

            SignInUiEvent.CreateAccountWithEmail -> {
                if (fieldsValidated()) {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                    scope.launch {
                        when (val result = signIn.createUserWithEmailExternally(
                            _state.value.email,
                            _state.value.password
                        )) {
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        error = result.throwable?.message?.let { it1 ->
                                            SignInError.valueOf(
                                                it1
                                            )
                                        }
                                    )
                                }
                            }

                            is Resource.Success -> {
                                onNavigate()
                            }
                        }

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

    private fun fieldsValidated(): Boolean {
        return when {
            _state.value.password != _state.value.retypePassword && state.value.mode == SignInMode.ACCOUNT_CREATE -> {
                _state.update {
                    it.copy(
                        error = SignInError.PASSWORD_NOT_SAME
                    )
                }
                false
            }

            _state.value.email.isBlank() -> {
                _state.update {
                    it.copy(
                        error = SignInError.EMAIL_BLANK
                    )
                }
                false

            }

            _state.value.password.isBlank() || _state.value.retypePassword.isBlank() && state.value.mode == SignInMode.ACCOUNT_CREATE -> {
                _state.update {
                    it.copy(
                        error = SignInError.PASSWORD_BLANK
                    )
                }
                false

            }

            _state.value.password.length < 6 -> {
                _state.update {
                    it.copy(
                        error = SignInError.PASSWORD_TOO_SHORT
                    )
                }
                false
            }

            !isValidEmail(_state.value.email) -> {
                _state.update {
                    it.copy(
                        error = SignInError.EMAIL_VALIDATION
                    )
                }
                false
            }

            else -> {
                true
            }
        }
    }
}

