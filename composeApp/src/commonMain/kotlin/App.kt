import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.jaegerapps.malmali.grammar.presentation.GrammarScreen
import com.jaegerapps.malmali.home.HomeScreen
import com.jaegerapps.malmali.login.presentation.SignInScreen
import com.jaegerapps.malmali.RootComponent
import com.jaegerapps.malmali.chat.presentation.ChatHomeScreen
import com.jaegerapps.malmali.loading.LoadingScreen
import com.jaegerapps.malmali.onboarding.completion.CompletionScreen
import com.jaegerapps.malmali.onboarding.personalization.PersonalizationScreen
import com.jaegerapps.malmali.onboarding.intro.IntroScreen
import com.jaegerapps.malmali.vocabulary.create_set.presentation.CreateSetScreen
import com.jaegerapps.malmali.vocabulary.folders.presentation.FolderScreen
import com.jaegerapps.malmali.vocabulary.study_flashcards.StudyFlashcardsScreen
import core.presentation.MalMalITheme
import kotlinx.coroutines.flow.subscribe

@Composable
fun App(
    darkTheme: Boolean,
    root: RootComponent,
) {
    val appState by root.state.collectAsState()
    LaunchedEffect(appState.loggedIn) {
        /*TODO - This won't work...*/
        if (!appState.loggedIn) {
//            root.onLogout()
        }
    }
    MalMalITheme(
        darkTheme
    ) {
        val childStack by root.childStack.subscribeAsState()

        Children(
            stack = childStack,
            animation = stackAnimation(fade())
        ) { child ->
            when (val instance = child.instance) {
                is RootComponent.Child.CreateSetScreen -> CreateSetScreen(component = instance.component)
                is RootComponent.Child.FlashcardHomeScreen -> FolderScreen(component = instance.component)
                is RootComponent.Child.StudyFlashcardsScreen -> StudyFlashcardsScreen(component = instance.component)
                is RootComponent.Child.HomeScreen -> HomeScreen(component = instance.component)
                is RootComponent.Child.GrammarScreen -> GrammarScreen(component = instance.component)
                is RootComponent.Child.SignInScreen -> SignInScreen(component = instance.component)
                is RootComponent.Child.IntroScreen -> IntroScreen(component = instance.component)
                is RootComponent.Child.PersonalizationScreen -> PersonalizationScreen(component = instance.component)
                is RootComponent.Child.CompletionScreen -> CompletionScreen(component = instance.component)
                is RootComponent.Child.LoadingScreen -> LoadingScreen(component = instance.component)
                is RootComponent.Child.ChatHomeScreen -> ChatHomeScreen(component = instance.component)
            }
        }
    }
}