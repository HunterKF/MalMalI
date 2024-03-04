package com.jaegerapps.malmali

import com.jaegerapps.malmali.vocabulary.domain.repo.VocabularyRepo
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.jaegerapps.malmali.chat.conversation.presentation.ConversationComponent
import com.jaegerapps.malmali.chat.home.presentation.ChatHomeComponent
import com.jaegerapps.malmali.components.models.Routes
import com.jaegerapps.malmali.di.AppModuleInterface
import com.jaegerapps.malmali.grammar.GrammarScreenComponent
import com.jaegerapps.malmali.grammar.models.GrammarLevel
import com.jaegerapps.malmali.home.HomeScreenComponent
import com.jaegerapps.malmali.loading.LoadingComponent
import com.jaegerapps.malmali.login.domain.UserData
import com.jaegerapps.malmali.login.presentation.SignInComponent
import com.jaegerapps.malmali.onboarding.intro.IntroComponent
import com.jaegerapps.malmali.vocabulary.presentation.create_set.CreateSetComponent
import com.jaegerapps.malmali.vocabulary.presentation.folders.FlashcardHomeComponent
import com.jaegerapps.malmali.vocabulary.presentation.study_flashcards.StudyFlashcardsComponent
import com.jaegerapps.malmali.onboarding.completion.CompletionComponent
import com.jaegerapps.malmali.onboarding.personalization.PersonalizationComponent
import com.jaegerapps.malmali.practice.presentation.PracticeComponent
import com.jaegerapps.malmali.vocabulary.domain.models.VocabSetModel
import core.Knower
import core.Knower.e
import core.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

class RootComponent(
    componentContext: ComponentContext,
    private val appModule: AppModuleInterface,
) : ComponentContext by componentContext {
    private val navigation = StackNavigation<Configuration>()
    private val scope = CoroutineScope(Dispatchers.IO)


    private val _state = MutableStateFlow(RootState())
    val state = _state
    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.LoadingScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    init {
        lifecycle.doOnCreate {

            scope.launch {
                getGrammar()
                val result = async { appModule.userRepo.retrieveAccessToken() }.await()
                when {
                    appModule.settingsDataSource.getOnboardingBoolean() -> {
                        navigation.replaceAll(Configuration.IntroScreen)
                    }

                    appModule.settingsDataSource.getToken() != null -> {
                        if (result != null) {
                            val user = async { appModule.settingsDataSource.getUser() }.await()
                            getFlashcards(user.nickname)

                            withContext(Dispatchers.Main) {
                                _state.update {
                                    it.copy(
                                        user = user,
                                        loggedIn = true
                                    )
                                }

                                navigation.replaceAll(Configuration.HomeScreen)

                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                _state.update {
                                    it.copy(
                                        user = null,
                                        loggedIn = false
                                    )
                                }

                                navigation.replaceAll(Configuration.SignInScreen)
                            }

                        }

                    }

                    appModule.settingsDataSource.getToken() == null -> {
                        _state.update {
                            it.copy(
                                user = null,
                                loggedIn = false
                            )
                        }
                        navigation.replaceAll(Configuration.SignInScreen)
                    }
                }
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
                        remoteId = config.remoteId,
                        setId = config.localId,
                        componentContext = context,
                        vocabFunctions = appModule.vocabularyRepo,
                        userData = _state.value.user!!,
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
                        repo = appModule.vocabularyRepo,
                        sets = _state.value.sets,
                        onNavigateBack = {
                            navigation.pop()
                        },
                        onNavigateToCreateScreen = {
                            navigation.pushNew(
                                Configuration.CreateSetScreen(
                                    appModule.vocabularyRepo,
                                    null,
                                    null,
                                )
                            )
                        },
                        onNavigateToStudyCard = { localId, remoteId->
                            navigation.pushNew(
                                Configuration.StudyFlashcardsScreen(
                                    appModule.vocabularyRepo,
                                    localId,
                                    remoteId
                                )
                            )

                        },
                        onNavigateToEdit = { setId, remoteId ->
                            navigation.pushNew(
                                Configuration.CreateSetScreen(
                                    appModule.vocabularyRepo,
                                    setId,
                                    remoteId
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
                Knower.e("pushStudy screen", "Current value of state sets it: ${state.value.sets}")
                Child.StudyFlashcardsScreen(
                    StudyFlashcardsComponent(
                        componentContext = context,
                        database = config.vocabFunctions,
                        onNavigate = { route ->
                            modalNavigate(route)
                        },
                        onCompleteNavigate = { navigation.pop() },
                        remoteId = config.remoteId,
                        setId = config.localId,
                        onEditNavigate = { localId, remoteId ->
                            navigation.pushNew(
                                Configuration.CreateSetScreen(
                                    appModule.vocabularyRepo,
                                    localId = localId,
                                    remoteId = remoteId
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
                        grammar = state.value.grammar,
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
                            scope.launch {
                                val token =
                                    async { appModule.userRepo.retrieveAccessToken() }.await()
                                token?.let {
                                    appModule.settingsDataSource.saveToken(it)
                                }
                            }

                        },
                        createUserOnDb = {
                            try {
                                appModule.signInRepo.createUserWithGmailExternally()
                                _state.update {
                                    it.copy(
                                        user = appModule.settingsDataSource.getUser(),
                                        loggedIn = true
                                    )
                                }
                            } catch (e: Exception) {
                                Knower.e("onCreateUser", "An error occurred! ㅠㅠ")
                            }

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
                        settings = appModule.settingsDataSource,
                        handleUser = appModule.userRepo,
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

            Configuration.ChatHomeScreen -> {
                Child.ChatHomeScreen(
                    ChatHomeComponent(
                        onModalNavigate = { route ->
                            modalNavigate(route)
                        },
                        chatRepo = appModule.chatRepo,
                        componentContext = context,
                        navigateToConversation = { title, background, iconTag ->
                            navigation.pushNew(
                                Configuration.ConversationScreen(
                                    title = title,
                                    background = background,
                                    topicTag = iconTag,
                                )
                            )
                        },
                    )
                )
            }

            is Configuration.ConversationScreen -> {
                Child.ConversationScreen(
                    ConversationComponent(
                        onNavigate = {
                            modalNavigate(it)
                        },
                        userData = _state.value.user!!,
                        api = appModule.chatRepo,
                        topic = config.title,
                        topicBackground = config.background,
                        topicIcon = config.topicTag,
                        componentContext = context
                    )
                )
            }

            Configuration.PracticeScreen -> {
                Child.PracticeScreen(
                    PracticeComponent(
                        onNavigate = {
                            modalNavigate(it)
                        },
                        practiceRepo = appModule.practiceRepo,
                        componentContext = context,
                        userData = _state.value.user!!,
                        grammarLevel = _state.value.grammar,
                        vocabularySets = _state.value.sets
                    )
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
        data class ChatHomeScreen(val component: ChatHomeComponent) : Child()
        data class ConversationScreen(val component: ConversationComponent) : Child()
        data class PracticeScreen(val component: PracticeComponent) : Child()
    }

    @OptIn(ExperimentalDecomposeApi::class)
    private fun modalNavigate(route: String) {
        when (route) {
            Routes.HOME -> navigation.popTo(0)
            Routes.VOCABULARY -> navigation.pushNew(Configuration.FlashcardHomeScreen(appModule.vocabularyRepo))
            Routes.GRAMMAR -> navigation.pushNew(Configuration.GrammarScreen)
            Routes.CHAT -> navigation.pushNew(Configuration.ChatHomeScreen)
            Routes.PRACTICE -> navigation.pushNew(Configuration.PracticeScreen)
            else -> {
                navigation.popTo(0)
            }
        }

    }

    fun onLogout() {
        navigation.replaceAll(Configuration.SignInScreen)
    }

    @Serializable
    sealed class Configuration {
        //Configuration defines your screens in the app

        @Serializable
        data class CreateSetScreen(
            val vocabFunctions: VocabularyRepo,
            val localId: Int?,
            val remoteId: Int?,
        ) : Configuration()

        @Serializable
        data class FlashcardHomeScreen(val vocabFunctions: VocabularyRepo) :
            Configuration()

        @Serializable
        data class StudyFlashcardsScreen(
            val vocabFunctions: VocabularyRepo,
            val localId: Int,
            val remoteId: Int,
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

        @Serializable
        data object ChatHomeScreen : Configuration()

        @Serializable
        data class ConversationScreen(
            val title: String,
            val background: String,
            val topicTag: String,
        ) : Configuration()

        @Serializable
        data object PracticeScreen : Configuration()
    }

    private suspend fun getFlashcards(user: String) {
        /*when (val sets = appModule.rootComponentUseCases.getSets(user)) {
            is Resource.Error -> {
                Knower.e(
                    "getDefaultFlashcards",
                    "An error has occurred here: ${sets.throwable?.message}"
                )

            }
            is Resource.Success -> {
                if (sets.data != null) {
                    _state.update {
                        it.copy(
                            sets = sets.data.map { it }
                        )
                    }
                }
            }
        }*/
    }

    private suspend fun getGrammar() {
        when (val result = appModule.rootComponentUseCases.getGrammar()) {
            is Resource.Error -> {
                Knower.e(
                    "getGrammar",
                    "An error occurred in the RootComponent. ${result.throwable}"
                )
            }

            is Resource.Success -> {
                if (result.data != null) {
                    _state.update {
                        it.copy(grammar = result.data)
                    }
                }
            }
        }
    }
}

data class RootState(
    val user: UserData? = null,
    val loggedIn: Boolean = false,
    val loading: Boolean = false,
    val grammar: List<GrammarLevel> = emptyList(),
    val sets: List<VocabSetModel> = emptyList(),
)