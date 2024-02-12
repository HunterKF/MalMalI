import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.jaegerapps.malmali.grammar.presentation.GrammarScreen
import com.jaegerapps.malmali.home.HomeScreen
import com.jaegerapps.malmali.login.presentation.SignInScreen
import com.jaegerapps.malmali.navigation.RootComponent
import com.jaegerapps.malmali.onboarding.completion.CompletionScreen
import com.jaegerapps.malmali.onboarding.personalization.PersonalizationScreen
import com.jaegerapps.malmali.onboarding.intro.IntroScreen
import com.jaegerapps.malmali.vocabulary.create_set.presentation.CreateSetScreen
import com.jaegerapps.malmali.screen_roots.ScreenA
import com.jaegerapps.malmali.screen_roots.ScreenB
import com.jaegerapps.malmali.vocabulary.folders.presentation.FolderScreen
import com.jaegerapps.malmali.vocabulary.study_flashcards.StudyFlashcardsScreen
import core.presentation.MalMalITheme
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(
    darkTheme: Boolean,
    root: RootComponent,
) {
    MalMalITheme(
        darkTheme
    ) {

        val childStack by root.childStack.subscribeAsState()

        Children(
            stack = childStack,
            animation = stackAnimation(slide())
        ) { child ->
            when (val instance = child.instance) {
                is RootComponent.Child.ScreenA -> ScreenA(component = instance.component)
                is RootComponent.Child.ScreenB -> ScreenB(component = instance.component)
                is RootComponent.Child.CreateSetScreen -> CreateSetScreen(component = instance.component)
                is RootComponent.Child.FlashcardHomeScreen -> FolderScreen(component = instance.component)
                is RootComponent.Child.StudyFlashcardsScreen -> StudyFlashcardsScreen(component = instance.component)
                is RootComponent.Child.HomeScreen -> HomeScreen(component = instance.component)
                is RootComponent.Child.GrammarScreen -> GrammarScreen(component = instance.component)
                is RootComponent.Child.SignInScreen -> SignInScreen(component = instance.component, client = root.client)
                is RootComponent.Child.WelcomeScreen -> IntroScreen(component = instance.component)
                is RootComponent.Child.PersonalizationScreen -> PersonalizationScreen(component = instance.component)
                is RootComponent.Child.CompletionScreen -> CompletionScreen(component = instance.component)
            }

        }
    }
}