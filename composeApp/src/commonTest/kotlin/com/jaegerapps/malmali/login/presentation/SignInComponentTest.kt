package com.jaegerapps.malmali.login.presentation

import app.cash.turbine.test
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import com.jaegerapps.malmali.core.FakeSignInImpl
import com.jaegerapps.malmali.login.domain.SignInRepo
import com.jaegerapps.malmali.navigation.RootComponent
import com.jaegerapps.malmali.data.FakeVocabularySetSourceFunctions
import com.jaegerapps.malmali.di.FakeAppModule
import com.russhwolf.settings.MapSettings
import core.util.Resource
import io.mockative.Mock
import io.mockative.classOf
import io.mockative.coEvery
import io.mockative.mock
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class SignInComponentTest {
    private lateinit var component: SignInComponent
    private val activeChild get() = component

    @Mock
    private val signInRepo = mock(classOf<SignInRepo>())



    @Test
    fun test() = runBlocking {

        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            coEvery { signInRepo.signInWithEmail("", "") }.returns(Resource.Error(Throwable()))
            assertEquals("", initialState.email)
            component.onEvent(SignInUiEvent.ChangeEmailValue("testing@gmail.com"))
            val emailState = awaitItem()
            assertEquals("testing@gmail.com", emailState.email)
            component.onEvent(SignInUiEvent.ChangePasswordValue("password123"))
            val passwordState = awaitItem()
            assertEquals("password123", passwordState.password)


            component.onEvent(SignInUiEvent.SignInWithEmail)
            val signInState = awaitItem()
            assertEquals("password123", signInState.password)
            val signInState2 = awaitItem()
            assertEquals("password123", signInState2.password)
        }
    }
    private fun createComponent(
    ) {
        val lifecycle = LifecycleRegistry()
        val root = RootComponent(
            componentContext = DefaultComponentContext(lifecycle = lifecycle),
            appModule = FakeAppModule()
        )
        component = SignInComponent(
            componentContext = root,
            signIn = FakeSignInImpl(),
            onNavigate = {

            },
        )


        lifecycle.resume()
    }
}