package com.jaegerapps.malmali.navigation

import VocabularySetSourceFunctions
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.pushNew
import com.jaegerapps.malmali.components.Routes
import com.jaegerapps.malmali.grammar.GrammarScreenComponent
import com.jaegerapps.malmali.grammar.data.GrammarRepoImpl
import com.jaegerapps.malmali.home.HomeScreenComponent
import com.jaegerapps.malmali.login.data.SignInImpl
import com.jaegerapps.malmali.login.presentation.SignInComponent
import com.jaegerapps.malmali.onboarding.welcome.WelcomeScreenComponent
import com.jaegerapps.malmali.vocabulary.create_set.presentation.CreateSetComponent
import com.jaegerapps.malmali.vocabulary.folders.FlashcardHomeComponent
import com.jaegerapps.malmali.vocabulary.study_flashcards.StudyFlashcardsComponent
import com.russhwolf.settings.Settings
import core.data.SupabaseClientFactory
import com.jaegerapps.malmali.login.data.UserDTO
import com.jaegerapps.malmali.onboarding.personalization.PersonalizationComponent
import kotlinx.serialization.Serializable

class RootComponent(
    componentContext: ComponentContext,
    private val vocabFunctions: VocabularySetSourceFunctions,
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()
    private val settings = Settings()
    val client = SupabaseClientFactory().createBase()
    val repo = GrammarRepoImpl(client)
    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.SignInScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    @OptIn(ExperimentalDecomposeApi::class)
    private fun createChild(
        config: Configuration,
        context: ComponentContext,
    ): Child {
        return when (config) {
            Configuration.ScreenA -> Child.ScreenA(
                ScreenAComponent(
                    componentContext = context,
                    onNavigateToScreenB = { text ->
                        navigation.pushNew(Configuration.ScreenB(text))
                    },
                    onNavigateToCreateSetScreen = {
                        modalNavigate(Routes.VOCABULARY)
//                        navigation.pushNew(Configuration.CreateSetScreen(vocabFunctions, null))

                    },
                    onNavigateToFlashcardHome = {
                        navigation.pushNew(Configuration.FlashcardHomeScreen(vocabFunctions))
                    }
                )
            )

            is Configuration.ScreenB -> {
                Child.ScreenB(
                    ScreenBComponent(
                        text = config.text,
                        componentContext = context,
                        onGoBack = {
                            navigation.pop()
                        }
                    )
                )
            }

            is Configuration.CreateSetScreen -> {
                Child.CreateSetScreen(
                    CreateSetComponent(
                        setTitle = config.title,
                        setId = config.id,
                        date = config.date,
                        componentContext = context,
                        database = vocabFunctions,
                        onComplete = {
                            navigation.pop()
                        },
                        onModalNavigate = {
                            modalNavigate(it)
                        }
                    )
                )
            }

            is Configuration.FlashcardHomeScreen -> {
                Child.FlashcardHomeScreen(
                    FlashcardHomeComponent(
                        componentContext = context,
                        database = vocabFunctions,
                        onNavigateBack = {
                            navigation.pop()
                        },
                        onNavigateToCreateScreen = {
                            navigation.pushNew(
                                Configuration.CreateSetScreen(
                                    vocabFunctions,
                                    null,
                                    null,
                                    null
                                )
                            )
                        },
                        onNavigateToStudyCard = { setId, title, date ->
                            navigation.pushNew(
                                Configuration.StudyFlashcardsScreen(
                                    vocabFunctions,
                                    setId,
                                    title,
                                    date
                                )
                            )

                        },
                        onNavigateToEdit = { title, setId, date ->
                            navigation.pushNew(
                                Configuration.CreateSetScreen(
                                    vocabFunctions,
                                    title,
                                    setId,
                                    date
                                )
                            )
                        },
                        onModalNavigate = {
                            modalNavigate(it)
                        }
                    )
                )
            }

            is Configuration.StudyFlashcardsScreen -> {
                Child.StudyFlashcardsScreen(
                    StudyFlashcardsComponent(
                        componentContext = context,
                        database = config.vocabFunctions,
                        setId = config.setId,
                        setTitle = config.title,
                        date = config.date,
                        onNavigate = { route ->
                            modalNavigate(route)
                        },
                        onCompleteNavigate = { navigation.pop() },
                        onEditNavigate = { title, id, date ->
                            navigation.pushNew(
                                Configuration.CreateSetScreen(
                                    vocabFunctions,
                                    title,
                                    id,
                                    date
                                )
                            )
                        }
                    )
                )
            }

            is Configuration.HomeScreen -> {
                Child.HomeScreen(
                    HomeScreenComponent(
                        componentContext = context,
                        userDTO = UserDTO(
                            user_nickname = "HunterK",
                            user_email = "hunter.krez@gmail.com",
                            user_id = "yes",
                            user_experience = 200,
                            user_achievements = arrayOf("Beginner"),
                            user_icon = "cat",
                        ),
                        onNavigate = { route ->
                            modalNavigate(route)
                        }
                    )
                )
            }

            is Configuration.GrammarScreen -> {
                Child.GrammarScreen(
                    GrammarScreenComponent(
                        componentContext = context,
                        onNavigate = { route ->
                            modalNavigate(route)
                        },
                        repo = repo
                    )
                )
            }

            is Configuration.WelcomeScreen -> {
                Child.WelcomeScreen(
                    WelcomeScreenComponent(
                        componentContext = context,
                        onNavigate = {

                        }
                    )
                )
            }

            is Configuration.SignInScreen -> {
                Child.SignInScreen(
                    SignInComponent(
                        componentContext = context,
                        onNavigate = {

                        },
                        signIn = SignInImpl(settings = settings, client)
                    )
                )
            }

            Configuration.PersonalizationScreen -> {
                Child.PersonalizationScreen(
                    PersonalizationComponent(componentContext = context)
                )
            }

        }
    }

    sealed class Child {
        data class ScreenA(val component: ScreenAComponent) : Child()
        data class ScreenB(val component: ScreenBComponent) : Child()
        data class CreateSetScreen(val component: CreateSetComponent) : Child()
        data class FlashcardHomeScreen(val component: FlashcardHomeComponent) : Child()
        data class StudyFlashcardsScreen(val component: StudyFlashcardsComponent) : Child()
        data class HomeScreen(val component: HomeScreenComponent) : Child()
        data class GrammarScreen(val component: GrammarScreenComponent) : Child()
        data class WelcomeScreen(val component: WelcomeScreenComponent) : Child()
        data class SignInScreen(val component: SignInComponent) : Child()
        data class PersonalizationScreen(val component: PersonalizationComponent) : Child()
    }

    @OptIn(ExperimentalDecomposeApi::class)
    private fun modalNavigate(route: String) {
        when (route) {
            Routes.HOME -> navigation.popTo(0)
            Routes.VOCABULARY -> navigation.pushNew(Configuration.FlashcardHomeScreen(vocabFunctions))
            Routes.GRAMMAR -> navigation.pushNew(Configuration.GrammarScreen)
            else -> {
                navigation.popTo(0)
            }
        }

    }

    @Serializable
    sealed class Configuration {
        //Configuration defines your screens in the app
        @Serializable
        data object ScreenA : Configuration()

        @Serializable
        data class ScreenB(val text: String) : Configuration()

        @Serializable
        data class CreateSetScreen(
            val vocabFunctions: VocabularySetSourceFunctions,
            val title: String?,
            val id: Long?,
            val date: Long?,
        ) : Configuration()

        @Serializable
        data class FlashcardHomeScreen(val vocabFunctions: VocabularySetSourceFunctions) :
            Configuration()

        @Serializable
        data class StudyFlashcardsScreen(
            val vocabFunctions: VocabularySetSourceFunctions,
            val setId: Long,
            val title: String,
            val date: Long,
        ) : Configuration()

        @Serializable
        data object HomeScreen : Configuration()

        @Serializable
        data object GrammarScreen : Configuration()

        @Serializable
        data object WelcomeScreen : Configuration()

        @Serializable
        data object SignInScreen : Configuration()

        @Serializable
        data object PersonalizationScreen : Configuration()
    }
}