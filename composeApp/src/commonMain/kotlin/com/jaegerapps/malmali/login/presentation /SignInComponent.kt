package com.jaegerapps.malmali.login.presentation

import com.arkivanov.decompose.ComponentContext
import com.jaegerapps.malmali.login.domain.SignInRepo
import core.data.SupabaseClient
import core.util.Resource
import io.github.jan.supabase.exceptions.BadRequestRestException
import io.github.jan.supabase.exceptions.NotFoundRestException
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.exceptions.UnauthorizedRestException
import io.github.jan.supabase.exceptions.UnknownRestException
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInComponent(
    componentContext: ComponentContext,
    private val signIn: SignInRepo,
    private val saveToken: suspend () -> Unit,
    private val createUserOnDb: suspend () -> Unit,
    private val onNavigate: () -> Unit,
) : ComponentContext by componentContext {
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
                _state.update {
                    it.copy(
                        isLoading = true
                    )
                }

                //This is AFTER we are successfully signed in. The next step is to create a user on the table.
                //Need to save the access token to the settings

                try {
                    //TODO - The issue with using this is I can't test it later. No way to separate the client OUT of the component. 어떡하지 어떡하지 ㅊㅊㅊ

                    //next we are going to create a user on the supabase db
                    //Need the email so we can do some policy filtering later, hence why we are adding it now.
                    val job = scope.launch {
                        async {
                            createUserOnDb()
                            saveToken()
                        }.await()
                        println("Async function happening")

                    }
                    job.invokeOnCompletion { throwable ->
                        println("Job has been invoked!")
                        if (throwable == null) {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    loginSuccess = true
                                )
                            }
                        } else {
                            _state.update {
                                it.copy(
                                    error = SignInError.UNKNOWN_ERROR
                                )
                            }
                        }
                    }
                } catch (e: RestException) {
                    _state.update {
                        it.copy(
                            error = e.toSignInError(),
                            isLoading = false
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
                                        error = (result.throwable as RestException).toSignInError()
                                    )
                                }
                            }

                            is Resource.Success -> {
                               _state.update {
                                   it.copy(
                                       loginSuccess = true
                                   )
                               }
                            }
                        }

                    }
                }
            }

            SignInUiEvent.SignInWithEmail -> {
                _state.update {
                    it.copy(
                        isLoading = true
                    )
                }
                scope.launch {
                    try {
                        when (val result =
                            signIn.signInWithEmail(_state.value.email, _state.value.password)) {
                            is Resource.Error -> _state.update {
                                it.copy(
                                    error = (result.throwable as RestException).toSignInError(),
                                    isLoading = false
                                )
                            }

                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        loginSuccess = true
                                    )
                                }
                            }
                        }
                    } catch (e: RestException) {
                        _state.update {
                            it.copy(
                                error = e.toSignInError(),
                                isLoading = true
                            )
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

            SignInUiEvent.LoginSuccess -> {
                onNavigate()
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

