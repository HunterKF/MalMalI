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
import com.jaegerapps.malmali.chat.presentation.conversation.ConversationComponent
import com.jaegerapps.malmali.chat.presentation.home.ChatHomeComponent
import com.jaegerapps.malmali.common.models.Routes
import com.jaegerapps.malmali.di.AppModuleInterface
import com.jaegerapps.malmali.grammar.presentation.GrammarScreenComponent
import com.jaegerapps.malmali.common.models.GrammarLevelModel
import com.jaegerapps.malmali.home.HomeScreenComponent
import com.jaegerapps.malmali.loading.LoadingComponent
import com.jaegerapps.malmali.login.domain.UserData
import com.jaegerapps.malmali.login.presentation.SignInComponent
import com.jaegerapps.malmali.onboarding.intro.IntroComponent
import com.jaegerapps.malmali.vocabulary.presentation.create_set.CreateSetComponent
import com.jaegerapps.malmali.vocabulary.presentation.folders.VocabularyHomeComponent
import com.jaegerapps.malmali.vocabulary.presentation.study_set.StudySetComponent
import com.jaegerapps.malmali.onboarding.completion.CompletionComponent
import com.jaegerapps.malmali.onboarding.personalization.PersonalizationComponent
import com.jaegerapps.malmali.practice.practice.presentation.PracticeComponent
import com.jaegerapps.malmali.common.models.VocabularySetModel
import com.jaegerapps.malmali.grammar.data.models.GrammarPointDTO
import com.jaegerapps.malmali.grammar.domain.mapper.toGrammarLevels
import com.jaegerapps.malmali.grammar.domain.mapper.toGrammarPoint
import com.jaegerapps.malmali.vocabulary.presentation.search.SearchSetComponent
import core.Knower
import core.Knower.d
import core.Knower.e
import core.data.supabase.SupabaseClient
import core.util.Resource
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
    private val scope = CoroutineScope(Dispatchers.Default)


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
        scope.launch {

            val grammar = async { getGrammar() }.await()

            grammar.data?.let { levels ->
                _state.update {
                    it.copy(
                        grammar = levels
                    )
                }
            }

            if (SupabaseClient.client.auth.currentSessionOrNull() != null) {
                SupabaseClient.client.auth.refreshCurrentSession()
                val user = appModule.settingsDataSource.getUser()
                when (val grammarResult = async { getGrammar() }.await()) {
                    is Resource.Error -> TODO()
                    is Resource.Success -> {
                        if (grammarResult.data != null) {
                            _state.update {
                                it.copy(
                                    user = user,
                                    grammar = grammarResult.data
                                )
                            }
                        }
                        Knower.d("init", "Here is the state. ${_state.value}")

                        withContext(Dispatchers.Main) {
                            navigation.replaceAll(Configuration.HomeScreen)
                        }
                    }
                }

            } else {
                navigation.replaceAll(Configuration.SignInScreen)
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
                        localId = config.localId,
                        isAuthor = config.isAuthor,
                        componentContext = context,
                        repo = appModule.vocabularyRepo,
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
                    VocabularyHomeComponent(
                        componentContext = context,
                        repo = appModule.vocabularyRepo,
                        sets = _state.value.sets,
                        onNavigateToCreateScreen = {
                            navigation.pushNew(
                                Configuration.CreateSetScreen(
                                    appModule.vocabularyRepo,
                                    null,
                                    null,
                                    null
                                )
                            )
                        },
                        onNavigateToStudyCard = { localId, remoteId ->
                            navigation.pushNew(
                                Configuration.StudyFlashcardsScreen(
                                    appModule.vocabularyRepo,
                                    localId,
                                    remoteId
                                )
                            )

                        },
                        onNavigateToEdit = { setId, remoteId, isAuthor ->
                            navigation.pushNew(
                                Configuration.CreateSetScreen(
                                    vocabFunctions = appModule.vocabularyRepo,
                                    localId = setId,
                                    remoteId = remoteId,
                                    isAuthor = isAuthor
                                )
                            )
                        },
                        onNavigateSearch = {
                            navigation.pushNew(Configuration.SearchScreen)
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
                    StudySetComponent(
                        componentContext = context,
                        database = config.vocabFunctions,
                        onNavigate = { route ->
                            modalNavigate(route)
                        },
                        onCompleteNavigate = { navigation.pop() },
                        remoteId = config.remoteId,
                        setId = config.localId,
                        onEditNavigate = { localId, remoteId, isAuthor ->
                            navigation.pushNew(
                                Configuration.CreateSetScreen(
                                    appModule.vocabularyRepo,
                                    localId = localId,
                                    remoteId = remoteId,
                                    isAuthor = isAuthor
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
                            Knower.e("HomeScreenNav", "Route: $route")
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
                        grammarLevelModels = _state.value.grammar,
                        isPro = true,
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
                        repo = appModule.practiceRepo,
                        componentContext = context,
                        userData = _state.value.user!!,
                        levelModelList = _state.value.grammar,
                    )
                )
            }

            Configuration.SearchScreen -> {
                Child.SearchScreen(
                    SearchSetComponent(
                        repo = appModule.vocabularyRepo,
                        onComplete = {
                            navigation.pop()
                        },
                        componentContext = context
                    )
                )
            }
        }
    }

    sealed class Child {
        data class CreateSetScreen(val component: CreateSetComponent) : Child()
        data class FlashcardHomeScreen(val component: VocabularyHomeComponent) : Child()
        data class StudyFlashcardsScreen(val component: StudySetComponent) : Child()
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
        data class SearchScreen(val component: SearchSetComponent) : Child()
    }

    @OptIn(ExperimentalDecomposeApi::class)
    private fun modalNavigate(route: String) {
        when (route) {
            Routes.HOME -> navigation.popTo(0)
            Routes.VOCABULARY -> navigation.pushNew(Configuration.FlashcardHomeScreen)
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
            val isAuthor: Boolean?,
        ) : Configuration()

        @Serializable
        data object FlashcardHomeScreen : Configuration()

        @Serializable
        data class StudyFlashcardsScreen(
            val vocabFunctions: VocabularyRepo,
            val localId: Int,
            val remoteId: Int,
        ) : Configuration()

        @Serializable
        data object HomeScreen : Configuration()

        @Serializable
        data object SearchScreen : Configuration()

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


    private suspend fun getGrammar(): Resource<List<GrammarLevelModel>> {
        return try {
            val data = SupabaseClient.client.from("grammar").select()
            Knower.d("getGrammar", "Here is the data: ${data.data}")

            val decode = data.decodeList<GrammarPointDTO>()
            Knower.d("getGrammar", "Here is the decode: ${decode}")

            val list = decode.map { it.toGrammarPoint() }
            Resource.Success(list.toGrammarLevels())
        } catch (e: RestException) {
            e.printStackTrace()
            Knower.e("getGrammar", "An error has occurred here. ${e.error}")
            Resource.Error(Throwable(e))

        }
    }

    private suspend fun checkIfSignedIn() {

    }
}

data class RootState(
    val user: UserData? = null,
    val loggedIn: Boolean = false,
    val loading: Boolean = false,
    val grammar: List<GrammarLevelModel> = emptyList(),
    val sets: List<VocabularySetModel> = emptyList(),
)