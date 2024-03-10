package com.jaegerapps.malmali.vocabulary.presentation.search

import app.cash.turbine.test
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import com.jaegerapps.malmali.RootComponent
import com.jaegerapps.malmali.core.FakeSignInImpl
import com.jaegerapps.malmali.data.FakeVocabularyRepo
import com.jaegerapps.malmali.di.FakeAppModule
import com.jaegerapps.malmali.login.presentation.SignInComponent
import core.Knower
import core.Knower.t
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class SearchSetComponentTest {


    private lateinit var component: SearchSetComponent
    private val activeChild get() = component

    @Test
    fun `Load More`() = runBlocking {
        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            Knower.t("Load More", "Part 1. Here is the start: ${initialState.startPageRange} and end: ${initialState.endPageRange}")
            Knower.t("Load More", "Part 1. Here are the start: ${initialState}")
            activeChild.onEvent(SearchUiEvent.LoadMore)
            val nextState = awaitItem()
            Knower.t("Load More", "Part 2. Here is the start: ${nextState.startPageRange} and end: ${nextState.endPageRange}")
            Knower.t("Load More", "Part 2. Here is the state: $nextState")

            assertEquals(true, nextState.loadingMore)
            val loadedState = awaitItem()
            Knower.t("Load More", "Part 3. Here is the start: ${loadedState.startPageRange} and end: ${loadedState.endPageRange}")
            Knower.t("Load More", "Part 3. Here is the state: $loadedState")

            assertEquals(false, loadedState.loadingMore)
            assertEquals(10, loadedState.startPageRange)
            assertEquals(19, loadedState.endPageRange)

        }
    }

    private fun createComponent(
    ) {
        val lifecycle = LifecycleRegistry()
        val root = RootComponent(
            componentContext = DefaultComponentContext(lifecycle = lifecycle),
            appModule = FakeAppModule()
        )
        component = SearchSetComponent(
            componentContext = root,
            repo = FakeVocabularyRepo(),
            onComplete = {

            }
        )


        lifecycle.resume()
    }
}