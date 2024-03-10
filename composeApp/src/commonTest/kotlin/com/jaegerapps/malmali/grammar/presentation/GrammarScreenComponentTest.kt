package com.jaegerapps.malmali.grammar.presentation

import app.cash.turbine.test
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import com.jaegerapps.malmali.RootComponent
import com.jaegerapps.malmali.data.FakeVocabularyRepo
import com.jaegerapps.malmali.di.FakeAppModule
import com.jaegerapps.malmali.grammar.domain.FakeGrammarRepo
import com.jaegerapps.malmali.grammar.domain.repo.GrammarRepo
import com.jaegerapps.malmali.grammar.models.GrammarLevel
import com.jaegerapps.malmali.grammar.models.GrammarPoint
import core.Knower
import core.Knower.t
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GrammarScreenComponentTest {


    private lateinit var component: GrammarScreenComponent
    private val activeChild get() = component
    private val grammarLevels = listOf(
        GrammarLevel(
            id = 1,
            title = "Basic Grammar",
            isSelected = false,
            isUnlocked = true,
            grammarList = listOf(
                GrammarPoint(
                    grammarCategory = 1,
                    grammarTitle = "Nouns",
                    grammarDef1 = "Definition of Nouns",
                    exampleEng1 = "The cat is sleeping.",
                    exampleEng2 = "John reads a book.",
                    exampleKor1 = "고양이가 자고 있다.",
                    exampleKor2 = "존이 책을 읽고 있다.",
                    selected = false
                ),
                // Add more GrammarPoints as needed
            )
        ),
        GrammarLevel(
            id = 2,
            title = "Intermediate Grammar",
            isSelected = false,
            isUnlocked = false,
            grammarList = listOf(
                GrammarPoint(
                    grammarCategory = 2,
                    grammarTitle = "Verbs",
                    grammarDef1 = "Definition of Verbs",
                    exampleEng1 = "She runs fast.",
                    exampleEng2 = "They are singing.",
                    exampleKor1 = "그녀는 빨리 달린다.",
                    exampleKor2 = "그들은 노래하고 있다.",
                    selected = false
                ),
                // Add more GrammarPoints as needed
            )
        ),
        // Repeat for levels 3 to 6
        GrammarLevel(
            id = 3,
            title = "Advanced Grammar",
            isSelected = false,
            isUnlocked = false,
            grammarList = listOf(
                // Add GrammarPoints
            )
        ),
        GrammarLevel(
            id = 4,
            title = "Professional Grammar",
            isSelected = false,
            isUnlocked = false,
            grammarList = listOf(
                // Add GrammarPoints
            )
        ),
        GrammarLevel(
            id = 5,
            title = "Expert Grammar",
            isSelected = false,
            isUnlocked = false,
            grammarList = listOf(
                // Add GrammarPoints
            )
        ),
        GrammarLevel(
            id = 6,
            title = "Master Grammar",
            isSelected = false,
            isUnlocked = false,
            grammarList = listOf(
                // Add GrammarPoints
            )
        )
    )


    @Test
    fun `Initialize Class - Expect Level 1 to be Selected`() = runBlocking {
        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            assertEquals(1, initialState.selectedLevels.first())
            assertTrue(initialState.selectedLevels.isNotEmpty())
            assertTrue(initialState.grammarLevelList.isNotEmpty())
        }
    }

    @Test
    fun `Toggle Editing`() = runBlocking {
        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            assertEquals(1, initialState.selectedLevels.first())
            assertTrue(initialState.selectedLevels.isNotEmpty())
            assertTrue(initialState.grammarLevelList.isNotEmpty())
            activeChild.onEvent(GrammarUiEvent.ToggleEditMode)
            val editState = awaitItem()
            assertEquals(true, editState.isEditing)
            activeChild.onEvent(GrammarUiEvent.ToggleEditMode)
            val offEditState = awaitItem()
            assertEquals(false, offEditState.isEditing)
        }
    }

    @Test
    fun `Select Level 2 - Add to list - Expect listOf 1 2`() = runBlocking {
        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            assertEquals(1, initialState.selectedLevels.first())
            assertTrue(initialState.selectedLevels.isNotEmpty())
            assertTrue(initialState.grammarLevelList.isNotEmpty())
            activeChild.onEvent(GrammarUiEvent.ToggleLevelSelection(grammarLevels[1]))
            val editState = awaitItem()
            assertEquals(2, editState.selectedLevels.size)
            Knower.t("Select Level 2", "Test state: $editState")
            assertEquals(2, editState.grammarLevelList.filter { it.isSelected }.size)
        }
    }

    @Test
    fun `Select Level 2 - Remove from list - Expect listOf 1`() = runBlocking {
        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            assertEquals(1, initialState.selectedLevels.first())
            assertTrue(initialState.selectedLevels.isNotEmpty())
            assertTrue(initialState.grammarLevelList.isNotEmpty())
            activeChild.onEvent(GrammarUiEvent.ToggleLevelSelection(grammarLevels[1]))
            val addedLevels = awaitItem()
            assertEquals(2, addedLevels.selectedLevels.size)

            Knower.t("Select Level 2", "Test state: $addedLevels")
            assertEquals(2, addedLevels.grammarLevelList.filter { it.isSelected }.size)
            assertEquals(2, addedLevels.selectedLevels.size)
            activeChild.onEvent(GrammarUiEvent.ToggleLevelSelection(grammarLevels[1]))
            val minusLevels = awaitItem()
            assertEquals(1, minusLevels.selectedLevels.size)
            Knower.t("Select Level 2", "Test state: $minusLevels")
            assertEquals(1, minusLevels.grammarLevelList.filter { it.isSelected }.size)
        }
    }

    private fun createComponent(
    ) {
        val lifecycle = LifecycleRegistry()
        val root = RootComponent(
            componentContext = DefaultComponentContext(lifecycle = lifecycle),
            appModule = FakeAppModule()
        )
        component = GrammarScreenComponent(
            componentContext = root,
            repo = FakeGrammarRepo(),
            isPro = false,
            grammarLevels = grammarLevels,
            onNavigate = {

            }
        )


        lifecycle.resume()
    }
}