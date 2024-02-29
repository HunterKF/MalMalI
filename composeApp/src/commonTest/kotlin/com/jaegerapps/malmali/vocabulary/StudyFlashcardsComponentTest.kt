package com.jaegerapps.malmali.vocabulary

import app.cash.turbine.test
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import com.jaegerapps.malmali.data.FakeVocabularyRepo
import com.jaegerapps.malmali.di.FakeAppModule
import com.jaegerapps.malmali.RootComponent
import com.jaegerapps.malmali.vocabulary.domain.models.VocabularyCardModel
import com.jaegerapps.malmali.vocabulary.presentation.study_flashcards.StudyError
import com.jaegerapps.malmali.vocabulary.presentation.study_flashcards.StudyFlashcardsComponent
import com.jaegerapps.malmali.vocabulary.presentation.study_flashcards.StudyFlashcardsUiEvent
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class StudyFlashcardsComponentTest {

    private lateinit var component: StudyFlashcardsComponent
    private val activeChild get() = component


    @Test
    fun loadAndGetData() = runBlocking {
        //Create and load initial state
        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            println(initialState)
            assertEquals(initialState.currentCard, null)
            val nextState = awaitItem()
            assertEquals(
                VocabularyCardModel(word = "먹다", definition = "to eat"),
                nextState.currentCard
            )
            assertEquals(
                expected = exampleVocabularyCardModelLists,
                actual = nextState.set!!.cards
            )
            assertEquals(
                nextState.set,
                exampleVocabSetModelLists.first()
            )
        }
    }

    @Test
    fun onEventOnBackCardClick() = runBlocking {
        //Flip the card to back and front
        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            println(initialState)
            assertEquals(initialState.currentCard, null)
            val nextState = awaitItem()
            assertEquals(
                VocabularyCardModel(word = "먹다", definition = "to eat"),
                nextState.currentCard
            )
            assertEquals(
                nextState.set!!.cards,
                exampleVocabularyCardModelLists
            )
            assertEquals(
                nextState.set,
                exampleVocabSetModelLists.first()
            )
            activeChild.onEvent(StudyFlashcardsUiEvent.OnFlipCard)
            val updatedTrueState = awaitItem()
            assertEquals(updatedTrueState.showBack, true)
            activeChild.onEvent(StudyFlashcardsUiEvent.OnFlipCard)
            val updatedFalseState = awaitItem()
            assertEquals(updatedFalseState.showBack, false)
        }
    }

    @Test
    fun onEventOnPreviousClick() = runBlocking {
        //Click previous card, expect error
        //index = 0, shouldn't be able to go back
        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            println(initialState)
            assertEquals(initialState.currentCard, null)
            val nextState = awaitItem()
            assertEquals(
                VocabularyCardModel(word = "먹다", definition = "to eat"),
                nextState.currentCard
            )
            assertEquals(
                nextState.set!!.cards,
                exampleVocabularyCardModelLists
            )
            assertEquals(
                nextState.set,
                exampleVocabSetModelLists.first()
            )
            assertEquals(
                nextState.currentIndex,
                0
            )
            //Start testing here
            activeChild.onEvent(StudyFlashcardsUiEvent.OnPrevious)
            val updatedTrueState = awaitItem()
            assertEquals(updatedTrueState.uiError, StudyError.NO_BACK)
        }
    }

    @Test
    fun onEventOnForwardClick() = runBlocking {
        //Click previous card, expect error
        //index = 0, shouldn't be able to go back
        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            println(initialState)
            assertEquals(initialState.currentCard, null)
            val nextState = awaitItem()
            assertEquals(
                VocabularyCardModel(word = "먹다", definition = "to eat"),
                nextState.currentCard
            )
            assertEquals(
                nextState.set!!.cards,
                exampleVocabularyCardModelLists
            )
            assertEquals(
                nextState.set,
                exampleVocabSetModelLists.first()
            )
            assertEquals(
                nextState.currentIndex,
                0
            )
            //Start testing here
            activeChild.onEvent(StudyFlashcardsUiEvent.OnForward)
            val updatedCardState = awaitItem()
            assertEquals(updatedCardState.currentIndex, 1)
            assertEquals(updatedCardState.currentCard, exampleVocabularyCardModelLists[1])
        }
    }

    @Test
    fun onEventForwardAndPrevious() = runBlocking {
        //Click previous card, expect error
        //index = 0, shouldn't be able to go back
        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            println(initialState)
            assertEquals(initialState.currentCard, null)
            val nextState = awaitItem()
            assertEquals(
                VocabularyCardModel(word = "먹다", definition = "to eat"),
                nextState.currentCard
            )
            assertEquals(
                nextState.set!!.cards,
                exampleVocabularyCardModelLists
            )
            assertEquals(
                nextState.set,
                exampleVocabSetModelLists.first()
            )
            assertEquals(
                nextState.currentIndex,
                0
            )
            //Start testing here
            activeChild.onEvent(StudyFlashcardsUiEvent.OnForward)
            val cardState = awaitItem()
            assertEquals(cardState.currentIndex, 1)
            assertEquals(cardState.currentCard, exampleVocabularyCardModelLists[1])
            activeChild.onEvent(StudyFlashcardsUiEvent.OnPrevious)
            val previousState = awaitItem()
            assertEquals(previousState.currentIndex, 0)
            assertEquals(previousState.currentCard, exampleVocabularyCardModelLists[0])
        }
    }

    @Test
    fun onEventForwardReachEnd() = runBlocking {
        //Go to end of list, expect isComplete to be true
        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            println(initialState)
            assertEquals(initialState.currentCard, null)
            val nextState = awaitItem()
            assertEquals(
                VocabularyCardModel(word = "먹다", definition = "to eat"),
                nextState.currentCard
            )
            assertEquals(
                exampleVocabularyCardModelLists,
                nextState.set!!.cards
            )
            assertEquals(
                nextState.set,
                exampleVocabSetModelLists.first()
            )
            assertEquals(
                nextState.currentIndex,
                0
            )
            println("nextState")
            println(nextState)
            //Start testing here
            activeChild.onEvent(StudyFlashcardsUiEvent.OnForward)
            val cardState1 = awaitItem()
            println("CardState1")
            println("CardState1")
            assertEquals(cardState1.currentIndex, 1)

            assertEquals(cardState1.currentCard, exampleVocabularyCardModelLists[1])
            activeChild.onEvent(StudyFlashcardsUiEvent.OnForward)
            val cardState2 = awaitItem()
            assertEquals(cardState2.currentIndex, 2)
            assertEquals(cardState2.currentCard, exampleVocabularyCardModelLists[2])
            activeChild.onEvent(StudyFlashcardsUiEvent.OnForward)
            val cardState3 = awaitItem()
            assertEquals(cardState3.currentIndex, 3)
            assertEquals(cardState3.currentCard, exampleVocabularyCardModelLists[3])
            activeChild.onEvent(StudyFlashcardsUiEvent.OnForward)
            val cardState4 = awaitItem()
            assertEquals(cardState4.currentIndex, 4)
            assertEquals(cardState4.currentCard, exampleVocabularyCardModelLists[4])
            println("The code is done: ${activeChild.state.value.currentIndex}")
            activeChild.onEvent(StudyFlashcardsUiEvent.OnForward)
            val onCompleteState = awaitItem()
            assertEquals(4, onCompleteState.currentIndex)
            assertEquals(exampleVocabularyCardModelLists[4], onCompleteState.currentCard)
            assertEquals(true, onCompleteState.isComplete)
            println("The code is done: ${activeChild.state.value.currentIndex}")
        }
    }

    @Test
    fun onEventPreviousAtIndexZero() = runBlocking {
        //Click previous, expect error message CAN'T GO FORWARD
        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            println(initialState)
            assertEquals(initialState.currentCard, null)
            val nextState = awaitItem()
            assertEquals(
                VocabularyCardModel(word = "먹다", definition = "to eat"),
                nextState.currentCard
            )
            assertEquals(
                nextState.set!!.cards,
                exampleVocabularyCardModelLists
            )
            assertEquals(
                nextState.set,
                exampleVocabSetModelLists.first()
            )
            assertEquals(
                nextState.currentIndex,
                0
            )
            activeChild.onEvent(StudyFlashcardsUiEvent.OnPrevious)
            val errorState = awaitItem()
            assertEquals(StudyError.NO_BACK, errorState.uiError)

        }
    }

    @Test
    fun onEventDontKnow() = runBlocking {
        //Click don't know button, expect that the current card is placed at the end of the ui list
        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            assertEquals(initialState.currentCard, null)
            val nextState = awaitItem()
            assertEquals(
                VocabularyCardModel(word = "먹다", definition = "to eat"),
                nextState.currentCard
            )
            assertEquals(
                nextState.set!!.cards,
                exampleVocabularyCardModelLists
            )
            assertEquals(
                nextState.set,
                exampleVocabSetModelLists.first()
            )
            assertEquals(
                nextState.currentIndex,
                0
            )
            activeChild.onEvent(StudyFlashcardsUiEvent.OnDontKnowClick)
            val dontKnowState = awaitItem()
            assertEquals(exampleVocabularyCardModelLists[0], dontKnowState.set!!.cards.last())
            assertEquals(exampleVocabularyCardModelLists.size, dontKnowState.set!!.cards.size)
            assertEquals(exampleVocabularyCardModelLists[1], dontKnowState.set!!.cards[0])
        }
    }

    @Test
    fun onEventDontKnow3Times() = runBlocking {
        //Click don't know button, expect that the current card is placed at the end of the ui list
        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            assertEquals(initialState.currentCard, null)
            val nextState = awaitItem()
            assertEquals(
                VocabularyCardModel(word = "먹다", definition = "to eat"),
                nextState.currentCard
            )
            assertEquals(
                nextState.set!!.cards,
                exampleVocabularyCardModelLists
            )
            assertEquals(
                nextState.set,
                exampleVocabSetModelLists.first()
            )
            assertEquals(
                nextState.currentIndex,
                0
            )
            activeChild.onEvent(StudyFlashcardsUiEvent.OnDontKnowClick)
            val dontKnowState1 = awaitItem()
            assertEquals(exampleVocabularyCardModelLists[0], dontKnowState1.set!!.cards.last())
            assertEquals(exampleVocabularyCardModelLists.size, dontKnowState1.set!!.cards.size)
            assertEquals(exampleVocabularyCardModelLists[1], dontKnowState1.set!!.cards[0])
            activeChild.onEvent(StudyFlashcardsUiEvent.OnDontKnowClick)
            val dontKnowState2 = awaitItem()
            assertEquals(exampleVocabularyCardModelLists[1], dontKnowState2.set!!.cards.last())
            assertEquals(exampleVocabularyCardModelLists.size, dontKnowState2.set!!.cards.size)
            assertEquals(exampleVocabularyCardModelLists[2], dontKnowState2.set!!.cards[0])
            activeChild.onEvent(StudyFlashcardsUiEvent.OnDontKnowClick)
            val dontKnowState3 = awaitItem()
            assertEquals(exampleVocabularyCardModelLists[2], dontKnowState3.set!!.cards.last())
            assertEquals(exampleVocabularyCardModelLists.size, dontKnowState3.set!!.cards.size)
            assertEquals(exampleVocabularyCardModelLists[3], dontKnowState3.set!!.cards[0])
        }
    }

    @Test
    fun onEventGotItToComplete() = runBlocking {
        //Click got it button 5 times, expect isComplete to be true
        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            assertEquals(initialState.currentCard, null)
            val nextState = awaitItem()
            assertEquals(
                VocabularyCardModel(word = "먹다", definition = "to eat"),
                nextState.currentCard
            )
            assertEquals(
                nextState.set!!.cards,
                exampleVocabularyCardModelLists
            )
            assertEquals(
                nextState.set,
                exampleVocabSetModelLists.first()
            )
            assertEquals(
                nextState.currentIndex,
                0
            )
            activeChild.onEvent(StudyFlashcardsUiEvent.OnForward)
            val cardState1 = awaitItem()
            println("CardState1")
            println("CardState1")
            assertEquals(cardState1.currentIndex, 1)
            assertEquals(cardState1.currentCard, exampleVocabularyCardModelLists[1])
            activeChild.onEvent(StudyFlashcardsUiEvent.OnForward)
            val cardState2 = awaitItem()
            assertEquals(cardState2.currentIndex, 2)
            assertEquals(cardState2.currentCard, exampleVocabularyCardModelLists[2])
            activeChild.onEvent(StudyFlashcardsUiEvent.OnForward)
            val cardState3 = awaitItem()
            assertEquals(cardState3.currentIndex, 3)
            assertEquals(cardState3.currentCard, exampleVocabularyCardModelLists[3])
            activeChild.onEvent(StudyFlashcardsUiEvent.OnForward)
            val cardState4 = awaitItem()
            assertEquals(cardState4.currentIndex, 4)
            assertEquals(cardState4.currentCard, exampleVocabularyCardModelLists[4])
            println("The code is done: ${activeChild.state.value.currentIndex}")
            activeChild.onEvent(StudyFlashcardsUiEvent.OnForward)
            val onCompleteState = awaitItem()
            assertEquals(4, onCompleteState.currentIndex)
            assertEquals(exampleVocabularyCardModelLists[4], onCompleteState.currentCard)
            assertEquals(true, onCompleteState.isComplete)
            println("The code is done: ${activeChild.state.value.currentIndex}")


        }
    }


    @Test
    fun onEventRepeatClick() = runBlocking {
        //Click repeat, expect the list to be ordered by level and current index to be 0
        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            println(initialState)
            assertEquals(initialState.currentCard, null)
            val nextState = awaitItem()
            assertEquals(
                VocabularyCardModel(word = "먹다", definition = "to eat"),
                nextState.currentCard
            )
            assertEquals(
                nextState.set!!.cards,
                exampleVocabularyCardModelLists
            )
            assertEquals(
                nextState.set,
                exampleVocabSetModelLists.first()
            )
            assertEquals(
                nextState.currentIndex,
                0
            )
            //Start testing here

            activeChild.onEvent(StudyFlashcardsUiEvent.OnForward)
            val cardState1 = awaitItem()
            assertEquals(cardState1.currentIndex, 1)
            assertEquals(cardState1.currentCard, exampleVocabularyCardModelLists[1])
            activeChild.onEvent(StudyFlashcardsUiEvent.OnForward)
            val cardState2 = awaitItem()
            assertEquals(cardState2.currentIndex, 2)
            assertEquals(cardState2.currentCard, exampleVocabularyCardModelLists[2])
            activeChild.onEvent(StudyFlashcardsUiEvent.OnForward)
            val cardState3 = awaitItem()
            assertEquals(cardState3.currentIndex, 3)
            assertEquals(cardState3.currentCard, exampleVocabularyCardModelLists[3])
            activeChild.onEvent(StudyFlashcardsUiEvent.OnForward)
            val cardState4 = awaitItem()
            assertEquals(cardState4.currentIndex, 4)
            assertEquals(cardState4.currentCard, exampleVocabularyCardModelLists[4])
            activeChild.onEvent(StudyFlashcardsUiEvent.OnForward)
            val onCompleteState = awaitItem()
            assertEquals(4, onCompleteState.currentIndex)
            assertEquals(exampleVocabularyCardModelLists[4], onCompleteState.currentCard)
            assertEquals(true, onCompleteState.isComplete)


            activeChild.onEvent(StudyFlashcardsUiEvent.OnRepeatClick)
            val repeatState = awaitItem()
            assertEquals(false, repeatState.isComplete)
            assertEquals(0, repeatState.currentIndex)
            assertEquals(exampleVocabularyCardModelLists[0], repeatState.currentCard)
        }
    }

    @Test
    fun onEventRepeatWithDontKnow() = runBlocking {
        //Click repeat, expect the list to be ordered by level and current index to be 0
        createComponent()
        activeChild.state.test {
            val initialState = awaitItem()
            println(initialState)
            assertEquals(initialState.currentCard, null)
            val nextState = awaitItem()
            assertEquals(
                VocabularyCardModel(word = "먹다", definition = "to eat"),
                nextState.currentCard
            )
            assertEquals(
                nextState.set!!.cards,
                exampleVocabularyCardModelLists
            )
            assertEquals(
                nextState.set,
                exampleVocabSetModelLists.first()
            )
            assertEquals(
                nextState.currentIndex,
                0
            )
            //Start testing here
            activeChild.onEvent(StudyFlashcardsUiEvent.OnForward)
            val cardState1 = awaitItem()
            assertEquals(cardState1.currentIndex, 1)
            assertEquals(cardState1.currentCard, exampleVocabularyCardModelLists[1])

            activeChild.onEvent(StudyFlashcardsUiEvent.OnForward)
            val cardState2 = awaitItem()
            assertEquals(cardState2.currentIndex, 2)
            assertEquals(cardState2.currentCard, exampleVocabularyCardModelLists[2])

            println("cardState2.currentCard")
            println(cardState2.currentCard)
            activeChild.onEvent(StudyFlashcardsUiEvent.OnDontKnowClick)
            val cardState3 = awaitItem()
            //currentCard of index 2 is now index of 4
            //id = 3, last should have id of 3
            assertEquals(cardState3.currentIndex, 2)
            assertEquals(cardState3.currentCard, exampleVocabularyCardModelLists[3])
            assertEquals(cardState3.set!!.cards.last(), exampleVocabularyCardModelLists[2])

            activeChild.onEvent(StudyFlashcardsUiEvent.OnForward)
            val cardState4 = awaitItem()
            assertEquals(cardState4.currentIndex, 3)
            assertEquals(cardState4.currentCard, exampleVocabularyCardModelLists[4])

            activeChild.onEvent(StudyFlashcardsUiEvent.OnForward)
            val cardState5 = awaitItem()
            assertEquals(cardState5.currentIndex, 4)
            assertEquals(cardState5.currentCard!!, exampleVocabularyCardModelLists[2])

            activeChild.onEvent(StudyFlashcardsUiEvent.OnForward)
            val onCompleteState = awaitItem()
            assertEquals(4, onCompleteState.currentIndex)
            assertEquals(exampleVocabularyCardModelLists[2], onCompleteState.currentCard!!)
            assertEquals(true, onCompleteState.isComplete)


            activeChild.onEvent(StudyFlashcardsUiEvent.OnRepeatClick)
            val repeatState = awaitItem()
            assertEquals(false, repeatState.isComplete)
            assertEquals(0, repeatState.currentIndex)
            assertEquals(exampleVocabularyCardModelLists[2], repeatState.currentCard!!)
        }
    }


    private fun createComponent(
    ) {
        val lifecycle = LifecycleRegistry()
        val root = RootComponent(
            componentContext = DefaultComponentContext(lifecycle = lifecycle),
            appModule = FakeAppModule()
        )
        component = StudyFlashcardsComponent(
            componentContext = root,
            database = FakeVocabularyRepo(),
            setId = -1,
            setTitle = "Set 1",
            onEditNavigate = { string, long, date -> },
            onCompleteNavigate = {},
            onNavigate = {},
            date = "11"
        )


        lifecycle.resume()
    }
}