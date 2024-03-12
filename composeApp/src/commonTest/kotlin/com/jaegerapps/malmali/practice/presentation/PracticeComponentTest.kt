package com.jaegerapps.malmali.practice.presentation

import app.cash.turbine.test
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import com.jaegerapps.malmali.RootComponent
import com.jaegerapps.malmali.common.models.GrammarLevelModel
import com.jaegerapps.malmali.common.models.GrammarPointModel
import com.jaegerapps.malmali.common.models.IconResource
import com.jaegerapps.malmali.di.FakeAppModule
import com.jaegerapps.malmali.login.domain.UserData
import com.jaegerapps.malmali.practice.domain.FakePracticeRepo
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class PracticeComponentTest {

    private lateinit var component: PracticeComponent
    private val activeChild get() = component

    private val grammarLevelModels = listOf(
        GrammarLevelModel(
            id = 1,
            title = "Basic Grammar",
            isSelected = false,
            isUnlocked = true,
            grammarList = listOf(
                GrammarPointModel(
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
        GrammarLevelModel(
            id = 2,
            title = "Intermediate Grammar",
            isSelected = false,
            isUnlocked = false,
            grammarList = listOf(
                GrammarPointModel(
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
        GrammarLevelModel(
            id = 3,
            title = "Advanced Grammar",
            isSelected = false,
            isUnlocked = false,
            grammarList = listOf(
                // Add GrammarPoints
            )
        ),
        GrammarLevelModel(
            id = 4,
            title = "Professional Grammar",
            isSelected = false,
            isUnlocked = false,
            grammarList = listOf(
                // Add GrammarPoints
            )
        ),
        GrammarLevelModel(
            id = 5,
            title = "Expert Grammar",
            isSelected = false,
            isUnlocked = false,
            grammarList = listOf(
                // Add GrammarPoints
            )
        ),
        GrammarLevelModel(
            id = 6,
            title = "Master Grammar",
            isSelected = false,
            isUnlocked = false,
            grammarList = listOf(
                // Add GrammarPoints
            )
        )
    )
    private val userData = UserData(
        nickname = "Test",
        experience = 1,
        currentLevel = 1,
        sets = listOf("1"),
        selectedLevels = listOf("1"),
        icon = IconResource.Bear_Fourteen
    )

    @Test
    fun `Initialize state`() = runBlocking {
        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            println(initialState)
            val loadedState = awaitItem()
            println(loadedState)


        }
    }


    private fun createComponent(
    ) {
        val lifecycle = LifecycleRegistry()
        val root = RootComponent(
            componentContext = DefaultComponentContext(lifecycle = lifecycle),
            appModule = FakeAppModule()
        )
        component = PracticeComponent(
            levelModelList = grammarLevelModels,
            userData = userData,
            onNavigate = {},
            repo = FakePracticeRepo(),
            componentContext = root
        )


        lifecycle.resume()
    }
}