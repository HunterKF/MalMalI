package com.jaegerapps.malmali.vocabulary

import app.cash.turbine.test
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import com.jaegerapps.malmali.data.FakeVocabularySetSourceFunctions
import com.jaegerapps.malmali.di.FakeAppModule
import com.jaegerapps.malmali.RootComponent
import com.jaegerapps.malmali.vocabulary.create_set.presentation.CreateSetComponent
import com.jaegerapps.malmali.vocabulary.create_set.presentation.CreateSetUiEvent
import com.jaegerapps.malmali.vocabulary.create_set.presentation.PopUpMode
import com.jaegerapps.malmali.vocabulary.create_set.presentation.SetMode
import com.jaegerapps.malmali.vocabulary.create_set.presentation.UiError
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class CreateSetComponentTest {

    private lateinit var component: CreateSetComponent
    private val activeChild get() = component

    @Test
    fun loadComponentWithoutVariables() = runBlocking {
        createComponent()

        activeChild.state.test {
            val initialState = awaitItem()
            assertEquals("", initialState.title)
            assertEquals(10, initialState.uiFlashcards.size)
            assertEquals(false, initialState.showSavePopUp)
            //Test after here
        }
    }

    @Test
    fun loadComponentWithVariables() = runBlocking {
        createComponent(
            title = "Set 1",
            setId = 1,
            date = "11"
        )

        activeChild.state.test {
            val initialState = awaitItem()
            assertEquals("", initialState.title)
            assertEquals(0, initialState.uiFlashcards.size)

            assertEquals(false, initialState.showSavePopUp)
            println("initialState")
            println(initialState)
            //Test after here
            val nextState1 = awaitItem()
            println("nextState1")
            println(nextState1)

            assertEquals(exampleUiFlashcardListWithUiId, nextState1.uiFlashcards)
            assertEquals(exampleVocabSetList[0].title, nextState1.title)
            assertEquals(exampleVocabSetList[0].isPublic, nextState1.isPublic)
        }
    }

    @Test
    fun loadComponentWithVariablesAndSave() = runBlocking {
        //add a card to the set
        createComponent(
            title = "Set 1",
            setId = 1,
            date = "11"
        )

        activeChild.state.test {
            val initialState = awaitItem()
            assertEquals("", initialState.title)
            assertEquals(0, initialState.uiFlashcards.size)

            assertEquals(false, initialState.showSavePopUp)
            println("initialState")
            println(initialState)
            //Test after here
            val nextState1 = awaitItem()
            println("nextState1")
            println(nextState1)

            assertEquals(exampleUiFlashcardListWithUiId, nextState1.uiFlashcards)
            assertEquals(exampleVocabSetList[0].title, nextState1.title)
            assertEquals(exampleVocabSetList[0].isPublic, nextState1.isPublic)
            activeChild.onEvent(CreateSetUiEvent.TogglePopUp(PopUpMode.SAVE))
            val popUpState = awaitItem()
            assertEquals(true, popUpState.showSavePopUp)
            assertEquals(PopUpMode.SAVE, popUpState.mode)
            activeChild.onEvent(CreateSetUiEvent.ConfirmPopUp)
            println(activeChild.state.value)
        }
    }
    @Test
    fun loadComponentWithVariablesChangePrivacy() = runBlocking {
        //add a card to the set
        createComponent(
            title = "Set 1",
            setId = 1,
            date = "11"
        )

        activeChild.state.test {
            val initialState = awaitItem()
            assertEquals("", initialState.title)
            assertEquals(0, initialState.uiFlashcards.size)

            assertEquals(false, initialState.showSavePopUp)
            println("initialState")
            println(initialState)
            //Test after here
            val nextState1 = awaitItem()
            println("nextState1")
            println(nextState1)

            assertEquals(exampleUiFlashcardListWithUiId, nextState1.uiFlashcards)
            assertEquals(exampleVocabSetList[0].title, nextState1.title)
            assertEquals(exampleVocabSetList[0].isPublic, nextState1.isPublic)
            activeChild.onEvent(CreateSetUiEvent.ChangePublicSetting(false))
            val privateState = awaitItem()
            assertEquals(false, privateState.isPublic)

            activeChild.onEvent(CreateSetUiEvent.TogglePopUp(PopUpMode.SAVE))
            val popUpState = awaitItem()
            assertEquals(false, privateState.isPublic)

            assertEquals(true, popUpState.showSavePopUp)
            assertEquals(PopUpMode.SAVE, popUpState.mode)
            activeChild.onEvent(CreateSetUiEvent.ConfirmPopUp)
            println(activeChild.state.value)
        }
    }

    @Test
    fun addCard() = runBlocking {
        //Add one card, initial list should be 10, new card should have id of 11
        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            assertEquals("", initialState.title)
            assertEquals(10, initialState.uiFlashcards.size)
            assertEquals(false, initialState.showSavePopUp)
            //Test after here
            activeChild.onEvent(CreateSetUiEvent.AddCard)
            val addedCardState = awaitItem()
            assertEquals("", addedCardState.uiFlashcards.last().word)
            assertEquals("", addedCardState.uiFlashcards.last().def)
            assertEquals(11, addedCardState.uiFlashcards.last().uiId)
        }
    }

    @Test
    fun changePublicSetting() = runBlocking {
        //change setting to public, then change back to private
        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            assertEquals("", initialState.title)
            assertEquals(10, initialState.uiFlashcards.size)
            assertEquals(false, initialState.showSavePopUp)
            //Test after here
            activeChild.onEvent(CreateSetUiEvent.ChangePublicSetting(true))
            val publicState = awaitItem()
            assertEquals(true, publicState.isPublic)
            activeChild.onEvent(CreateSetUiEvent.ChangePublicSetting(false))
            val privateState = awaitItem()
            assertEquals(false, privateState.isPublic)
        }
    }

    @Test
    fun toggleSavePopUpExpectTitleError() = runBlocking {
        //user would click save, then the popup should appear.
//       //Title is blank, so expect error MISSING_TITLE
        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            assertEquals("", initialState.title)
            assertEquals(10, initialState.uiFlashcards.size)
            assertEquals(false, initialState.showSavePopUp)
            //Test after here
            activeChild.onEvent(CreateSetUiEvent.TogglePopUp(PopUpMode.SAVE))
            val errorState = awaitItem()
            assertEquals(UiError.MISSING_TITLE, errorState.error)
        }
    }

    @Test
    fun toggleSavePopUpExpectWordError() = runBlocking {
        //user would click save, then the popup should appear.
        //Title is blank, so expect error MISSING_WORD

        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            assertEquals("", initialState.title)
            assertEquals(10, initialState.uiFlashcards.size)
            assertEquals(false, initialState.showSavePopUp)
            //Test after here
            activeChild.onEvent(CreateSetUiEvent.EditTitle("testing"))
            val titleState = awaitItem()
            assertEquals("testing", titleState.title)

            activeChild.onEvent(CreateSetUiEvent.TogglePopUp(PopUpMode.SAVE))
            val checkList = awaitItem()
            assertEquals(null, checkList.error)
            val errorState = awaitItem()
            assertEquals(UiError.MISSING_WORD, errorState.error)
        }
    }

    @Test
    fun toggleSavePopUpExpectShowPopUpTrue() = runBlocking {
        //edit all words, add a title, show pop up should be true
        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            assertEquals("", initialState.title)
            assertEquals(10, initialState.uiFlashcards.size)
            assertEquals(false, initialState.showSavePopUp)
            //Test after here
            activeChild.onEvent(CreateSetUiEvent.EditTitle("testing"))
            val titleState = awaitItem()
            assertEquals("testing", titleState.title)

            for (i in 1..10) {
                activeChild.onEvent(CreateSetUiEvent.EditWord(i, text = "Word $i"))
                val wordChangedState = awaitItem()
                assertEquals("Word $i", wordChangedState.uiFlashcards[i - 1].word)
                activeChild.onEvent(CreateSetUiEvent.EditDef(i, "Def $i"))
                val defChangedState = awaitItem()
                assertEquals("Def $i", defChangedState.uiFlashcards[i - 1].def)
            }


            activeChild.onEvent(CreateSetUiEvent.TogglePopUp(PopUpMode.SAVE))
            val popUpState = awaitItem()
            assertEquals(true, popUpState.showSavePopUp)
        }
    }

    @Test
    fun onClearUiError() = runBlocking {
        //get error, clear it, expect error to be null
        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            assertEquals("", initialState.title)
            assertEquals(10, initialState.uiFlashcards.size)
            assertEquals(false, initialState.showSavePopUp)
            //Test after here
            activeChild.onEvent(CreateSetUiEvent.EditTitle("testing"))
            val titleState = awaitItem()
            assertEquals("testing", titleState.title)

            for (i in 1..9) {
                activeChild.onEvent(CreateSetUiEvent.EditWord(i, text = "Word $i"))
                val wordChangedState = awaitItem()
                assertEquals("Word $i", wordChangedState.uiFlashcards[i - 1].word)
                activeChild.onEvent(CreateSetUiEvent.EditDef(i, "Def $i"))
                val defChangedState = awaitItem()
                assertEquals("Def $i", defChangedState.uiFlashcards[i - 1].def)
            }


            activeChild.onEvent(CreateSetUiEvent.TogglePopUp(PopUpMode.SAVE))
            val checkList = awaitItem()
            assertEquals(null, checkList.error)
            val errorState = awaitItem()
            assertEquals(UiError.MISSING_WORD, errorState.error)
            activeChild.onEvent(CreateSetUiEvent.OnClearUiError)

            val clearedState = awaitItem()
            assertEquals(null, clearedState.error)
        }
    }

    @Test
    fun blankTest() = runBlocking {
        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            assertEquals("", initialState.title)
            assertEquals(10, initialState.uiFlashcards.size)
            assertEquals(false, initialState.showSavePopUp)
            //Test after here

        }

    }

    private fun createComponent(
        title: String? = null,
        setId: Int? = null,
        date: String? = null
    ) {
        val lifecycle = LifecycleRegistry()
        val root = RootComponent(
            componentContext = DefaultComponentContext(lifecycle = lifecycle),
            appModule = FakeAppModule()
        )
        component = CreateSetComponent(
            setTitle = title,
            setId = setId,
            componentContext = root,
            vocabFunctions = FakeVocabularySetSourceFunctions(),
            onComplete = {},
            onModalNavigate = { string -> },
            date = date
        )


        lifecycle.resume()
    }
}