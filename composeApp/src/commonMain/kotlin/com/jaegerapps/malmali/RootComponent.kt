package com.jaegerapps.malmali

import VocabularySetSourceFunctions
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnResume
import com.jaegerapps.malmali.components.Routes
import com.jaegerapps.malmali.di.AppModuleInterface
import com.jaegerapps.malmali.grammar.GrammarScreenComponent
import com.jaegerapps.malmali.home.HomeScreenComponent
import com.jaegerapps.malmali.loading.LoadingComponent
import com.jaegerapps.malmali.login.domain.UserData
import com.jaegerapps.malmali.login.presentation.SignInComponent
import com.jaegerapps.malmali.onboarding.intro.IntroComponent
import com.jaegerapps.malmali.vocabulary.create_set.presentation.CreateSetComponent
import com.jaegerapps.malmali.vocabulary.folders.FlashcardHomeComponent
import com.jaegerapps.malmali.vocabulary.study_flashcards.StudyFlashcardsComponent
import com.jaegerapps.malmali.onboarding.completion.CompletionComponent
import com.jaegerapps.malmali.onboarding.personalization.PersonalizationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class RootComponent(
    componentContext: ComponentContext,
    private val appModule: AppModuleInterface,
) : ComponentContext by componentContext {
    private val navigation = StackNavigation<Configuration>()
    private val scope = CoroutineScope(Dispatchers.IO)
    private val initialScreen = mutableStateOf<Configuration>(Configuration.LoadingScreen)

    private val _state = MutableStateFlow(RootState())
    val state = _state
    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = initialScreen.value,
        handleBackButton = true,
        childFactory = ::createChild
    )

    init {
        lifecycle.doOnResume {
            scope.launch {
                appModule.userFunctions.refreshAccessToken()
            }
        }
        lifecycle.doOnCreate {
            scope.launch {
                when {
                    appModule.settingFunctions.getToken() != null -> {
                        appModule.userFunctions.refreshAccessToken()
                        _state.update {
                            it.copy(
                                user = appModule.settingFunctions.getUser(),
                                loggedIn = true
                            )
                        }
                        initialScreen.value = Configuration.HomeScreen
                    }

                    appModule.settingFunctions.getToken() == null -> {
                        _state.update {
                            it.copy(
                                user = null,
                                loggedIn = false
                            )
                        }
                        initialScreen.value = Configuration.SignInScreen
                    }

                    appModule.settingFunctions.getOnboardingBoolean() -> {
                        initialScreen.value = Configuration.IntroScreen
                    }
                }
            }

            scope.launch {
                /*Need a way to
                * 1. Check if the access token exists.
                * 2. If it exists, refresh the access token and save the new one
                * 3. If it doesn't exist, then the user does not exist, so we should move the app into logged out state.
                * What if we make the user a global state, inside the RootComponent. You won't have to track the user anywhere.
                * Updating the user would be a pain though.
                * Am I stupid...?*/
                appModule.userFunctions.refreshAccessToken()
            }
        }
    }

    @OptIn(ExperimentalDecomposeApi::class)
    private fun createChild(
        config: Configuration,
        context: ComponentContext,
    ): Child {

        return when (config) {
            is Configuration.CreateSetScreen -> {
                Child.CreateSetScreen(
                    CreateSetComponent(
                        setTitle = config.title,
                        setId = config.id,
                        date = config.date,
                        componentContext = context,
                        vocabFunctions = appModule.vocabFunctions,
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
                        database = appModule.vocabFunctions,
                        onNavigateBack = {
                            navigation.pop()
                        },
                        onNavigateToCreateScreen = {
                            navigation.pushNew(
                                Configuration.CreateSetScreen(
                                    appModule.vocabFunctions,
                                    null,
                                    null,
                                    null
                                )
                            )
                        },
                        onNavigateToStudyCard = { setId, title, date ->
                            navigation.pushNew(
                                Configuration.StudyFlashcardsScreen(
                                    appModule.vocabFunctions,
                                    setId,
                                    title,
                                    date
                                )
                            )

                        },
                        onNavigateToEdit = { title, setId, date ->
                            navigation.pushNew(
                                Configuration.CreateSetScreen(
                                    appModule.vocabFunctions,
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
                                    appModule.vocabFunctions,
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
                        user = state.value.user!!,
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
                        repo = appModule.grammarRepo
                    )
                )
            }

            is Configuration.IntroScreen -> {
                Child.IntroScreen(
                    IntroComponent(
                        componentContext = context,
                        onNavigate = {
                            navigation.pushNew(Configuration.SignInScreen)
                        }
                    )
                )
            }

            is Configuration.SignInScreen -> {
                Child.SignInScreen(
                    SignInComponent(
                        componentContext = context,
                        saveToken = {
                            appModule.settingFunctions.saveToken()
                        },
                        createUserOnDb = {
                            appModule.signInRepo.createUserWithGmailExternally()
                        },
                        onNavigate = {
                            navigation.pushNew(Configuration.PersonalizationScreen)
                        },
                        signIn = appModule.signInRepo,
                    )
                )
            }

            Configuration.PersonalizationScreen -> {
                Child.PersonalizationScreen(
                    PersonalizationComponent(
                        componentContext = context,
                        settings = appModule.settingFunctions,
                        handleUser = appModule.userFunctions,
                        onNavigate = {
                            navigation.pushNew(Configuration.CompletionScreen)
                        })
                )
            }

            Configuration.CompletionScreen -> {
                Child.CompletionScreen(
                    CompletionComponent(
                        componentContext = context,
                        onNavigate = {
                            navigation.replaceAll(Configuration.HomeScreen)
                        }
                    )
                )
            }

            Configuration.LoadingScreen -> {
                Child.LoadingScreen(
                    LoadingComponent(componentContext = context)
                )
            }
        }
    }

    sealed class Child {
        data class CreateSetScreen(val component: CreateSetComponent) : Child()
        data class FlashcardHomeScreen(val component: FlashcardHomeComponent) : Child()
        data class StudyFlashcardsScreen(val component: StudyFlashcardsComponent) : Child()
        data class HomeScreen(val component: HomeScreenComponent) : Child()
        data class GrammarScreen(val component: GrammarScreenComponent) : Child()
        data class IntroScreen(val component: IntroComponent) : Child()
        data class SignInScreen(val component: SignInComponent) : Child()
        data class PersonalizationScreen(val component: PersonalizationComponent) : Child()
        data class CompletionScreen(val component: CompletionComponent) : Child()
        data class LoadingScreen(val component: LoadingComponent) : Child()
    }

    @OptIn(ExperimentalDecomposeApi::class)
    private fun modalNavigate(route: String) {
        when (route) {
            Routes.HOME -> navigation.popTo(0)
            Routes.VOCABULARY -> navigation.pushNew(Configuration.FlashcardHomeScreen(appModule.vocabFunctions))
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
        data object LoadingScreen : Configuration()

        @Serializable
        data object GrammarScreen : Configuration()

        @Serializable
        data object IntroScreen : Configuration()

        @Serializable
        data object SignInScreen : Configuration()

        @Serializable
        data object PersonalizationScreen : Configuration()

        @Serializable
        data object CompletionScreen : Configuration()
    }
}

data class RootState(
    val user: UserData? = null,
    val loggedIn: Boolean = false,
    val loading: Boolean = false,
)