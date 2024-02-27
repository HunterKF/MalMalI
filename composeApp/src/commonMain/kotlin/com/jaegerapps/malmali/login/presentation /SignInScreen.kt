package com.jaegerapps.malmali.login.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.components.blackBorder
import core.data.supabase.SupabaseClient
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth

@Composable
fun SignInScreen(
    component: SignInComponent,
) {
    val state by component.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var string: StringResource? by remember { mutableStateOf(null) }
    val errorString: MutableState<String?> = mutableStateOf(string?.let { stringResource(it) })
    LaunchedEffect(key1 = state.error, key2 = errorString) {
        string = when (state.error) {
            SignInError.PASSWORD_NOT_SAME -> MR.strings.sign_in_error_password_incorrect
            SignInError.EMAIL_VALIDATION -> MR.strings.sign_in_error_email_validation
            SignInError.EMAIL_BLANK -> MR.strings.sign_in_error_email_blank
            SignInError.PASSWORD_BLANK -> MR.strings.sign_in_error_password_blank
            SignInError.NETWORK_ERROR -> MR.strings.sign_in_error_network_error
            SignInError.UNKNOWN_ERROR -> MR.strings.sign_in_error_network_error
            SignInError.PASSWORD_TOO_SHORT -> MR.strings.sign_in_error_password_too_short
            SignInError.ALREADY_REGISTERED -> MR.strings.sign_in_error_email_already_registered
            SignInError.PASSWORD_EMAIL_INVALID -> MR.strings.sign_in_error_password_email_invalid
            SignInError.EMAIL_NOT_FOUND -> MR.strings.sign_in_error_email_not_found
            null -> null
        }

        errorString.value?.let {
            snackbarHostState.showSnackbar(message = it)
            component.onEvent(SignInUiEvent.ClearError)
        }
    }

    LaunchedEffect(state.loginSuccess) {
        if (state.loginSuccess) {
            component.onEvent(SignInUiEvent.LoginSuccess)
        }
    }



    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier.fillMaxWidth().weight(1f).padding(it).blackBorder()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                when (state.mode) {
                    SignInMode.SIGN_IN -> SignInContent(
                        email = state.email,
                        password = state.password,
                        passwordVisible = passwordVisible,
                        onUiEvent = { component.onEvent(it) }
                    ) {
                        passwordVisible = !passwordVisible
                    }

                    SignInMode.ACCOUNT_CREATE -> {
                        CreateAccountContent(
                            email = state.email,
                            password = state.password,
                            retypePassword = state.retypePassword,
                            passwordVisible = passwordVisible,
                            onUiEvent = { component.onEvent(it) }

                        ) {
                            passwordVisible = !passwordVisible
                        }
                    }
                }

            }
            TextButton(onClick = {
                component.onEvent(SignInUiEvent.ToggleMode)
            }) {
                Text(
                    text = if (state.mode == SignInMode.SIGN_IN) stringResource(MR.strings.create_account_already_have) else stringResource(
                        MR.strings.create_account_dont_have
                    )
                )
            }
        }
    }

}

@Composable
private fun CreateAccountContent(
    email: String,
    password: String,
    retypePassword: String,
    passwordVisible: Boolean,
    onUiEvent: (SignInUiEvent) -> Unit,
    onVisibilityToggle: () -> Unit,

    ) {
    Text(stringResource(MR.strings.create_account_title))
    OutlinedTextField(
        modifier = Modifier.blackBorder(),
        shape = RoundedCornerShape(25.dp),
        value = email,
        onValueChange = { newValue ->
            onUiEvent(SignInUiEvent.ChangeEmailValue(newValue))
        },
        placeholder = {
            Text(stringResource(MR.strings.sign_in_placeholder_email))
        },
        singleLine = true,

        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email
        ),
    )
    OutlinedTextField(
        modifier = Modifier.blackBorder(),
        shape = RoundedCornerShape(25.dp),
        value = password,
        onValueChange = { newValue ->
            onUiEvent(SignInUiEvent.ChangePasswordValue(newValue))
        },
        placeholder = {
            Text(text = stringResource(MR.strings.sign_in_placeholder_password))
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password
        ),
        singleLine = true,

        trailingIcon = {
            /*https://stackoverflow.com/questions/65304229/toggle-password-field-jetpack-compose*/
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            // Please provide localized description for accessibility services
            val description =
                if (passwordVisible) stringResource(MR.strings.sign_in_content_desc_hide_password) else stringResource(
                    MR.strings.sign_in_content_desc_show_password
                )

            IconButton(onClick = { onVisibilityToggle() }) {
                Icon(imageVector = image, description)
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()

    )
    OutlinedTextField(
        modifier = Modifier.blackBorder(),
        shape = RoundedCornerShape(25.dp),
        value = retypePassword,
        onValueChange = { newValue ->
            onUiEvent(SignInUiEvent.ChangeRetypePasswordValue(newValue))
        },
        placeholder = {
            Text(text = stringResource(MR.strings.sign_in_placeholder_re_password))
        },
        isError = retypePassword != password,
        singleLine = true,

        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password
        ),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
    Text(stringResource(MR.strings.sign_in_privacy_policy))
    TextButton(
        onClick = {
            onUiEvent(SignInUiEvent.CreateAccountWithEmail)
        }
    ) {
        Text(
            text = stringResource(MR.strings.create_account_title)
        )
    }
    OrDivider()

    GoogleSignInButton {
        onUiEvent(it)
    }
    TextButton(
        onClick = {

        }
    ) {
        Text(
            text = stringResource(MR.strings.sign_in_with_apple)
        )
    }
}

@Composable
private fun SignInContent(
    email: String,
    password: String,
    passwordVisible: Boolean,
    onUiEvent: (SignInUiEvent) -> Unit,
    onVisibilityToggle: () -> Unit,
) {
    Text(stringResource(MR.strings.sign_in_prompt_sign_in))
    OutlinedTextField(
        modifier = Modifier.blackBorder(),
        value = email,
        onValueChange = { newValue ->
            onUiEvent(SignInUiEvent.ChangeEmailValue(newValue))
        },
        singleLine = true,

        placeholder = {
            Text(stringResource(MR.strings.sign_in_placeholder_email))
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email
        ),
    )
    OutlinedTextField(
        modifier = Modifier.blackBorder(),
        shape = RoundedCornerShape(25.dp),
        value = password,
        onValueChange = { newValue ->
            onUiEvent(SignInUiEvent.ChangePasswordValue(newValue))
        },
        placeholder = {
            Text(text = stringResource(MR.strings.sign_in_placeholder_password))
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password
        ),
        trailingIcon = {
            /*https://stackoverflow.com/questions/65304229/toggle-password-field-jetpack-compose*/
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            // Please provide localized description for accessibility services
            val description =
                if (passwordVisible) stringResource(MR.strings.sign_in_content_desc_hide_password) else stringResource(
                    MR.strings.sign_in_content_desc_show_password
                )

            IconButton(onClick = { onVisibilityToggle() }) {
                Icon(imageVector = image, description)
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()

    )
    Text(stringResource(MR.strings.sign_in_privacy_policy))
    TextButton(
        onClick = {
            onUiEvent(SignInUiEvent.SignInWithEmail)
        }
    ) {
        Text(
            text = stringResource(MR.strings.sign_in_prompt_sign_in)
        )
    }
    OrDivider()


    GoogleSignInButton(
        onUiEvent = {
            onUiEvent(it)
        }
    )
    TextButton(
        onClick = {

        }
    ) {
        Text(
            text = stringResource(MR.strings.sign_in_with_apple)
        )
    }
}

@Composable
private fun GoogleSignInButton(
    onUiEvent: (SignInUiEvent) -> Unit,
) {
    val client = SupabaseClient.client
    val authState = client.composeAuth.rememberSignInWithGoogle(
        onResult = {
            when (it) { //handle errors
                NativeSignInResult.ClosedByUser -> {

                }

                is NativeSignInResult.Error -> {
                    onUiEvent(SignInUiEvent.OnError(SignInError.UNKNOWN_ERROR))
                }

                is NativeSignInResult.NetworkError -> {
                    onUiEvent(SignInUiEvent.OnError(SignInError.NETWORK_ERROR))
                }

                NativeSignInResult.Success -> {
                    onUiEvent(
                        SignInUiEvent.SignInWithGmailSuccess
                    )
                }
            }
        }
    )
    TextButton(
        onClick = {
            authState.startFlow()
        }
    ) {
        Text(
            text = stringResource(MR.strings.sign_in_with_google)
        )
    }
}

@Composable
private fun OrDivider(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(Modifier.weight(1f))
        Text(
            text = stringResource(MR.strings.sign_in_placeholder_or),
            modifier = Modifier.padding(12.dp)
        )
        Divider(Modifier.weight(1f))
    }
}