package com.jaegerapps.malmali.login.presentation

import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import com.jaegerapps.malmali.login.data.SignInRepoImpl
import com.jaegerapps.malmali.login.domain.SignInRepo
import com.jaegerapps.malmali.navigation.RootComponent
import com.jaegerapps.malmali.vocabulary.FakeVocabularySetSourceFunctions
import com.russhwolf.settings.MapSettings
import io.github.jan.supabase.SupabaseClient
import io.mockative.classOf
import io.mockative.mock
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test

class SignInComponentTest {
    private lateinit var component: SignInComponent
    private val activeChild get() = component
    private lateinit var signIn: SignInRepo

    @BeforeTest
    fun setup() {
        signIn = SignInRepoImpl(
            settings = MapSettings.Factory().create(),
            supabase = mock(classOf<SupabaseClient>())
        )
    }

    @Test
    fun test() = runBlocking {
        createComponent()
        signIn.createUserLocally("hunter.krez@gmail.com", userId = "HunterK300")
    }


    private fun createComponent() {
        val lifecycle = LifecycleRegistry()
        val root = RootComponent(
            componentContext = DefaultComponentContext(lifecycle = lifecycle),
            vocabFunctions = FakeVocabularySetSourceFunctions()
        )
        component = SignInComponent(
            componentContext = root,
            signIn = signIn,
            onNavigate = {

            }
        )


        lifecycle.resume()
    }
}